package com.xinyunlian.jinfu.external.service;

import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.external.dto.*;
import com.xinyunlian.jinfu.external.dto.req.LoanApplyReq;
import com.xinyunlian.jinfu.external.dto.req.RegisterReq;
import com.xinyunlian.jinfu.external.enums.ConfirmationType;
import com.xinyunlian.jinfu.external.enums.PaymentOptionType;
import com.xinyunlian.jinfu.external.util.ATZImageUtil;
import com.xinyunlian.jinfu.external.util.Base64Util;
import com.xinyunlian.jinfu.loan.dao.LoanApplDao;
import com.xinyunlian.jinfu.loan.dto.req.LoanCustomerApplDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.loan.entity.LoanApplPo;
import com.xinyunlian.jinfu.loan.enums.EApplOutType;
import com.xinyunlian.jinfu.loan.enums.ERepayMode;
import com.xinyunlian.jinfu.loan.service.LoanApplService;
import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;
import com.xinyunlian.jinfu.schedule.dto.OverdueDetailDto;
import com.xinyunlian.jinfu.schedule.dto.ScheduleDto;
import com.xinyunlian.jinfu.schedule.enums.EScheduleStatus;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by godslhand on 2017/6/17.
 */
@Service(value = "externalServiceImpl")
public class ExternalServiceImpl implements ExternalService {

    @Autowired
    ATZApiService atzApiService;
    @Autowired
    LoanApplOutService loanApplOutService;
    @Autowired
    LoanApplOutUserService loanApplOutUserService;

    @Autowired
    LoanApplOutRefundsAdvanceService loanApplOutRefundsAdvanceService;
    @Autowired
    LoanApplDao loanApplDao;
    Logger logger = LoggerFactory.getLogger(ExternalServiceImpl.class);

    @Override
    public String loanApply(ExternalLoanApplyDto externalLoanApplyDto) {
//        logger.debug("externalLoanApplyDto:{}", JsonUtil.toJson(externalLoanApplyDto));
        //判断是否已经注册
        if (loanApplOutUserService.findByUserIdAndType(externalLoanApplyDto.getUserId(), EApplOutType.AITOUZI) == null) {

            LoanApplOutUserDto outUserDto = new LoanApplOutUserDto();
            outUserDto.setUserId(externalLoanApplyDto.getUserId());
            outUserDto.setOutUserId(externalLoanApplyDto.getRegisterReq().getIdNo());
            outUserDto.setType(EApplOutType.AITOUZI);


            changePath2Base64(externalLoanApplyDto.getRegisterReq());
            atzApiService.register(externalLoanApplyDto.getRegisterReq());
            loanApplOutUserService.save(outUserDto);
        }

        LoanApplPo loanApplpo = loanApplDao.findOne(externalLoanApplyDto.getApplId());
//        logger.debug("loanApplDto:{}", JsonUtil.toJson(loanApplpo));

        LoanApplyReq loanApplyReq = new LoanApplyReq();
        loanApplyReq.setAmount(loanApplpo.getApprAmt().doubleValue());
        loanApplyReq.setIdNo(externalLoanApplyDto.getRegisterReq().getIdNo());
        loanApplyReq.setLoanPurpose("经营需要");
        loanApplyReq.setLoanTerm(loanApplpo.getTermType().getDay(loanApplpo.getApprTermLen()));

        //云随带
        if (loanApplpo.getRepayMode() == ERepayMode.INTR_PER_DIEM) {
            loanApplyReq.setPaymentOption(
                    Integer.valueOf(PaymentOptionType.once.getCode()));
        }
        else if(loanApplpo.getRepayMode() == ERepayMode.MONTH_AVE_CAP_PLUS_INTR) {//云速贷
            loanApplyReq.setPaymentOption(
                    Integer.valueOf(PaymentOptionType.thirty_period.getCode()));
        }

        return atzApiService.loanApply(loanApplyReq);
    }

    @Override
    public boolean loanContractConfirm(String applyId, ConfirmationType confirmationType) {
        LoanApplOutDto loanApplOutDto = getSafeLoanApplOutDto(applyId);
        return atzApiService.loanContractConfirm(loanApplOutDto.getOutTradeNo(), confirmationType);
    }

    @Override
    public List<ScheduleDto> getOverDueLoanDetailInfo(String applyId) {
        LoanApplOutDto loanApplOutDto = getSafeLoanApplOutDto(applyId);
        OverDueLoanDtl overDueLoanDtl= atzApiService.getOverDueLoanDetailInfo(loanApplOutDto.getOutTradeNo());
        return convert2ScheduleDtos(overDueLoanDtl.getRefunds(),applyId);
    }

    @Override
    public RefundsAdvanceDto loanRefundInAdvance(String applyId) {
        LoanApplOutDto loanApplOutDto = getSafeLoanApplOutDto(applyId);
        RefundsAdvanceDto dto =  atzApiService.loanRefundInAdvance(loanApplOutDto.getOutTradeNo());
        dto.setApplyId(applyId);
        loanApplOutRefundsAdvanceService.save(dto);
        return dto;
    }

    @Override
    public boolean loanRefuandInAdvanceConfirm(String applyId, ConfirmationType confirmationType) {
        LoanApplOutDto loanApplOutDto = getSafeLoanApplOutDto(applyId);
       RefundsAdvanceDto dto = loanApplOutRefundsAdvanceService.findByApplId(applyId);
       if(dto==null||dto.getLoanRefuandInAdvanceId()==null)
           throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, "未找到外部的提前还款编号");
        loanApplOutRefundsAdvanceService.updateDisableTag(applyId,dto.getLoanRefuandInAdvanceId()); //进来就使用掉，每次发起必须重新申请
        new Thread(() -> atzApiService.loanRefuandInAdvanceConfirm(loanApplOutDto.getOutTradeNo(),
                dto.getLoanRefuandInAdvanceId(),confirmationType)).start();
        return true;
    }

    @Override
    public List<ScheduleDto> getLoanPaymentPlan(String applId) {
     LoanApplOutDto loanApplOutDto= getSafeLoanApplOutDto(applId);
     List<RefundDto> refundDtos= atzApiService.getLoanPaymentPlan(loanApplOutDto.getOutTradeNo());
        return convert2ScheduleDtos(refundDtos,applId);
    }

    @Override
    public EFinanceSourceType getCode() {
        return EFinanceSourceType.AITOUZI;
    }

    private List<ScheduleDto> convert2ScheduleDtos(List<RefundDto> refundDtos,String applId) {

        List<ScheduleDto> scheduleDtos = new ArrayList<>();
        if (CollectionUtils.isEmpty(refundDtos))
            return scheduleDtos;
        for (RefundDto refundDto : refundDtos) {
            scheduleDtos.add(convert2ScheduleDto(refundDto,applId));
        }
        return scheduleDtos;
    }

    private ScheduleDto convert2ScheduleDto(RefundDto refundDto,String applId) {

        String loanId = "L" + applId;//生成系统的loanId;
        ScheduleDto scheduleDto = new ScheduleDto();
        scheduleDto.setDueDate(changeDateFormat(refundDto.getDueDate()));
        scheduleDto.setAcctNo("0");
        scheduleDto.setScheduleStatus(EScheduleStatus.NOTYET);
        scheduleDto.setSeqNo(Integer.valueOf(refundDto.getPeriodNumber()));
        if (refundDto.getDueInterest() != null)
            scheduleDto.setShouldInterest(new BigDecimal(refundDto.getDueInterest()));
        if (refundDto.getDueCapital() != null)
            scheduleDto.setShouldCapital(new BigDecimal(refundDto.getDueCapital()));
        if(refundDto.getDefaultInterest()!=null)//TODO 待确认字段，罚息
            scheduleDto.setShouldFineCapital(new BigDecimal(refundDto.getDefaultInterest()));
        scheduleDto.setScheduleId(loanId + "_" + scheduleDto.getSeqNo());  //系统规则
        scheduleDto.setLoanId(loanId);

        return scheduleDto;
    }

    private String changeDateFormat(String data){
        if(data==null )
            return data;
        try {

            Date dudate = new SimpleDateFormat("yyyyMMdd").parse(data);

            return  new SimpleDateFormat("yyyy-MM-dd").format(dudate);

        } catch (ParseException e) {
            return null;
        }

    }
    /**
     * 根据系统的loanId获取外部的loanId
     *
     * @param loanId
     * @return
     */
    private LoanApplOutDto getSafeLoanApplOutDto(String loanId) {

        LoanApplOutDto loanApplOutDto = loanApplOutService.findByApplIdAndType
                (loanId, EApplOutType.AITOUZI);
        if (loanApplOutDto == null)
            throw new BizServiceException(EErrorCode.COMM_INTERNAL_ERROR, "未找到外部系统对应的贷款编号");
        return loanApplOutDto;
    }


    private void changePath2Base64(RegisterReq registerReq) {
        registerReq.setBussinesLicencePhoto(getBase64(registerReq.getBussinesLicencePhoto()));
        registerReq.setInsidePhoto(getBase64(registerReq.getInsidePhoto()));
        registerReq.setOutsidePhoto(getBase64(registerReq.getOutsidePhoto()));
        registerReq.setOrganizationCodePhoto(getBase64(registerReq.getOrganizationCodePhoto()));
        registerReq.setTaxRegistrationPhoto(getBase64(registerReq.getTaxRegistrationPhoto()));
        registerReq.setIdPhotoFront(getBase64(registerReq.getIdPhotoFront()));
    }

    private String getBase64(String url) {
        logger.info("photoUrl:"+url);
        if (StringUtils.isEmpty(url))
            return "";
        try {
            url = getCompressURL(url);
            logger.info("photoUrl compressURL:"+url);
            File file =  ATZImageUtil.download(url);

            String filestr = Base64Util.byteToBase64Encoding(Base64Util.getFileByte(file));
            file.delete();
            return filestr;
        } catch (Exception e) {
            logger.error("地址转换出错", e);
            return "";
        }
    }

    /**
     * 默认取420*420大小，目前系统支持130x130  228x228 420x420
     *
     * @param url
     * @return
     */
    private String getCompressURL(String url) {
        return  url.replace("https","http");
    }


}
