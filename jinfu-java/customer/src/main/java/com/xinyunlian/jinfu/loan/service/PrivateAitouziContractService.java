package com.xinyunlian.jinfu.loan.service;

import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.*;
import com.xinyunlian.jinfu.contract.dto.*;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import com.xinyunlian.jinfu.contract.enums.ESignedMark;
import com.xinyunlian.jinfu.contract.service.ContractService;
import com.xinyunlian.jinfu.external.dto.LoanApplOutBankCardDto;
import com.xinyunlian.jinfu.external.dto.RefundsAdvanceDto;
import com.xinyunlian.jinfu.external.enums.ConfirmationType;
import com.xinyunlian.jinfu.external.service.ExternalService;
import com.xinyunlian.jinfu.external.service.LoanApplOutBankCardService;
import com.xinyunlian.jinfu.loan.dto.RepayDtlDto;
import com.xinyunlian.jinfu.loan.dto.aitz.ATZSureDto;
import com.xinyunlian.jinfu.loan.dto.aitz.AitouziContractDto;
import com.xinyunlian.jinfu.loan.dto.RepayDtlDto;
import com.xinyunlian.jinfu.loan.dto.aitz.AtzRepayDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.enums.ERepayStatus;
import com.xinyunlian.jinfu.overdue.dto.OverdueDayDetailDto;
import com.xinyunlian.jinfu.overdue.dto.OverdueMonthDetailDto;
import com.xinyunlian.jinfu.product.service.LoanProductService;
import com.xinyunlian.jinfu.repay.service.RepayService;
import com.xinyunlian.jinfu.schedule.dto.ScheduleDto;
import com.xinyunlian.jinfu.schedule.enums.EScheduleStatus;
import com.xinyunlian.jinfu.user.dto.UserExtDto;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.math.BigDecimal;

import java.text.MessageFormat;
import java.util.*;

/**
 * @author willwang
 * 爱投资合同操作
 */
@Service
public class PrivateAitouziContractService {

    @Autowired
    private UserService userService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private LoanApplOutBankCardService loanApplOutBankCardService;

    @Autowired
    private LoanApplService loanApplService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private ExternalService externalService;

    @Autowired
    private LoanProductService loanProductService;

    @Autowired
    private RepayService repayService;

    @Value(value = "${local_domain}")
    private String LOCAL_DOMAIN;

    /**
     * 绑定银行卡页面, {0} -> 身份证号码, {1} -> channelId, {2} -> name
     */
    @Value(value = "${atz.bind.card.url}")
    private String BIND_CARD_URL;

    /**
     * 爱投资绑卡成功页面
     */
    @Value(value = "${atz.bind.card.success.url}")
    private String BIND_CARD_SUCCESS_URL;

    /**
     * 爱投资channelID
     */
    @Value(value = "${aitz.channelId}")
    private String ATZ_CHANNEL_ID;

    private static Logger LOGGER = LoggerFactory.getLogger(PrivateAitouziContractService.class);

    /**
     * 爱投资贷款合同地址
     */
    public static final String ATZ_LOAN_URL = "/jinfu/web/loan/atz/contract/view/loan";

    /**
     * 获取所有爱投资相关的合同
     * @param applId
     * @return
     */
    public List<AitouziContractDto> list(String applId) {
        String userId = SecurityContext.getCurrentUserId();

        List<AitouziContractDto> rs = new ArrayList<>();

        ECntrctTmpltType contractType = ECntrctTmpltType.ATZ_LOAN;

        UserContractDto userContractDto = contractService.getUserContractByBizId(userId, contractType, applId);
        AitouziContractDto t1 = new AitouziContractDto();
        t1.setName(contractType.getText());
        t1.setType(contractType.getCode());
        t1.setSigned(userContractDto != null && userContractDto.getSignedMark() != null && userContractDto.getSignedMark() == ESignedMark.FIRST_SIGN);
        rs.add(t1);

        return rs;
    }

    /**
     * 爱投资确认贷款
     * @param applId
     * @return
     */
    public ATZSureDto bind(String applId){

        ATZSureDto atzSureDto = new ATZSureDto();

        String userId = SecurityContext.getCurrentUserId();

        //绑银行卡前检查合同签署状态
        UserContractDto userContractDto = contractService.getUserContractByBizId(userId, ECntrctTmpltType.ATZ_LOAN, applId);

        if(userContractDto == null || userContractDto.getSignedMark() != ESignedMark.FIRST_SIGN){
            throw new BizServiceException(EErrorCode.LOAN_CONTRACT_NOT_FINISH, "合同签署尚未完成");
        }

        UserInfoDto userInfoDto = userService.findUserByUserId(userId);

        if(LOGGER.isDebugEnabled()){
            LOGGER.debug("BIND_CARD_URL:{}", BIND_CARD_URL);
            LOGGER.debug("ATZ_CHANNEL_ID:{}", ATZ_CHANNEL_ID);
            LOGGER.debug("BIND_CARD_SUCCESS_URL:{}", BIND_CARD_SUCCESS_URL);
        }

        atzSureDto.setBindCardUrl(
            MessageFormat.format(BIND_CARD_URL, userInfoDto.getIdCardNo(), ATZ_CHANNEL_ID, userInfoDto.getUserName())
        );

        atzSureDto.setSuccessUrl(BIND_CARD_SUCCESS_URL);

        //用户是否绑定过银行卡信息
        LoanApplOutBankCardDto loanApplOutBankCardDto = loanApplOutBankCardService.findByUserLatest(userId);

        atzSureDto.setHasCard(loanApplOutBankCardDto != null);

        return atzSureDto;
    }


    /**
    /**
     * 爱投资申请提前还款
     * @param loanId
     * @return
     * @throws BizServiceException
     */
    public AtzRepayDto repayStart(String loanId) throws BizServiceException{

        AtzRepayDto atzRepayDto = new AtzRepayDto();

        LoanDtlDto loanDtlDto = loanService.get(loanId);

        this.checkYours(loanDtlDto);

        //检查是否有还款中的贷款
        this.checkRepaying(loanDtlDto);
        RefundsAdvanceDto refundsAdvanceDto;
        try{
            refundsAdvanceDto = externalService.loanRefundInAdvance(loanDtlDto.getApplId());
        }catch(Exception e){
            throw new BizServiceException(EErrorCode.LOAN_ATZ_REPAY_MODIFY, "订单状态已变更，请重新尝试", e);
        }

        UserInfoDto userInfoDto = userService.findUserByUserId(loanDtlDto.getUserId());
        LoanApplOutBankCardDto loanApplOutBankCardDto = loanApplOutBankCardService.findByApplId(loanDtlDto.getApplId());

        atzRepayDto.setAmt(NumberUtil.roundTwo(refundsAdvanceDto.getAmount()));
        atzRepayDto.setCapital(NumberUtil.roundTwo(refundsAdvanceDto.getCapital()));
        atzRepayDto.setInterest(NumberUtil.roundTwo(refundsAdvanceDto.getInterest()));
        atzRepayDto.setName(userInfoDto.getUserName());
        atzRepayDto.setIdCardNo(MaskUtil.maskMiddleValue(userInfoDto.getIdCardNo()));
        atzRepayDto.setBankCardNo(MaskUtil.maskMiddleValue(loanApplOutBankCardDto.getBankCardNo()));
        atzRepayDto.setDesc(
            MessageUtil.getMessage("loan.atz.repay.confirm.desc", atzRepayDto.getAmt(), atzRepayDto.getCapital(), atzRepayDto.getInterest())
        );
        return atzRepayDto;
    }

    /**
     * 提前还款确认
     * @param loanId
     * @throws BizServiceException
     */
    public void repayConfirm(String loanId) throws BizServiceException{
        LoanDtlDto loanDtlDto = loanService.get(loanId);
        this.checkYours(loanDtlDto);

        //预还款操作
        repayService.externalInAdvance(loanId);

        //发起外部确认请求
        externalService.loanRefuandInAdvanceConfirm(loanDtlDto.getApplId(), ConfirmationType.agree);
    }

    /**
     * 爱投资贷款的逾期概况
     * @param loanId
     * @return
     * @throws BizServiceException
     */
    public Object overdue(String loanId) throws BizServiceException{

        Object rs = null;

        LoanDtlDto loanDtlDto = loanService.get(loanId);

        com.xinyunlian.jinfu.product.dto.LoanProductDetailDto product = loanProductService.getProdDtl(loanDtlDto.getProdId());

        this.checkYours(loanDtlDto);

        List<ScheduleDto> scheduleDtos = externalService.getOverDueLoanDetailInfo(loanDtlDto.getApplId());

        switch(loanDtlDto.getRepayMode()){
            case INTR_PER_DIEM:
                OverdueDayDetailDto overdueDayDetailDto = new OverdueDayDetailDto();
                overdueDayDetailDto.setDays(0);
                overdueDayDetailDto.setFine(BigDecimal.ZERO);
                overdueDayDetailDto.setSurplus(BigDecimal.ZERO);
                if(scheduleDtos.size() > 0){
                    ScheduleDto scheduleDto = scheduleDtos.get(0);
                    overdueDayDetailDto.setSurplus(AmtUtils.positive(loanDtlDto.getLoanAmt().subtract(loanDtlDto.getRepayedAmt())));
                    overdueDayDetailDto.setDays(LoanUtils.getFineDays(scheduleDto.getDueDate()));
                    product.getFineType().getFine(
                        overdueDayDetailDto.getSurplus(), overdueDayDetailDto.getDays(), product.getFineValue()
                    );
                }
                rs = overdueDayDetailDto;
                break;
            case MONTH_AVE_CAP_PLUS_INTR:
                List<OverdueMonthDetailDto> list = new ArrayList<>();

                if(scheduleDtos.size() > 0){
                    for (ScheduleDto scheduleDto : scheduleDtos) {
                        if(scheduleDto.getScheduleStatus() != EScheduleStatus.OVERDUE){
                            continue;
                        }
                        OverdueMonthDetailDto item = new OverdueMonthDetailDto();
                        item.setDays(
                                LoanUtils.getFineDays(scheduleDto.getDueDate())
                        );
                        item.setPeriod(scheduleDto.getSeqNo());
                        item.setTotalPeriod(loanDtlDto.getTermLen());
                        item.setCapital(NumberUtil.roundTwo(scheduleDto.getShouldCapital()));
                        item.setInterest(NumberUtil.roundTwo(scheduleDto.getShouldInterest()));
                        item.setFine(NumberUtil.roundTwo(scheduleDto.getShouldFineCapital()));

                        list.add(item);
                    }
                }

                rs = list;
                break;
        }

        return rs;
    }

    private void checkYours(LoanDtlDto loanDtlDto){
        String userId = SecurityContext.getCurrentUserId();
        if(!userId.equals(loanDtlDto.getUserId())){
            throw new BizServiceException(EErrorCode.LOAN_APPL_NOT_YOURS, "不是自己的贷款，无法操作");
        }
    }

    private void checkRepaying(LoanDtlDto loanDtlDto){
        List<RepayDtlDto> list = repayService.listByLoanId(loanDtlDto.getLoanId());
        if(CollectionUtils.isEmpty(list)){
            return;
        }
        for (RepayDtlDto repayDtlDto : list) {
            if(Arrays.asList(ERepayStatus.PROCESS, ERepayStatus.PAY).contains(repayDtlDto.getStatus())){
                throw new BizServiceException(EErrorCode.LOAN_REPAY_EXCEED_QUOTA, "还款处理中，无法重新发起");
            }
        }
    }

}
