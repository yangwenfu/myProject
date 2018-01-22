package com.xinyunlian.jinfu.loan.service;

import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.car.dto.UserCarDto;
import com.xinyunlian.jinfu.car.service.UserCarService;
import com.xinyunlian.jinfu.cashier.api.JinfuCashierSignature;
import com.xinyunlian.jinfu.cashier.dto.LoanPayDto;
import com.xinyunlian.jinfu.cashier.dto.ProcessRetDto;
import com.xinyunlian.jinfu.cashier.service.LoanPayService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.sms.SmsUtil;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.MessageUtil;
import com.xinyunlian.jinfu.common.util.NumberUtil;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import com.xinyunlian.jinfu.contract.service.ContractService;
import com.xinyunlian.jinfu.creditline.enums.ELoanUserCreditLineStatus;
import com.xinyunlian.jinfu.creditline.service.LoanUserCreditLineService;
import com.xinyunlian.jinfu.dealer.dto.DealerUserOrderDto;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserOrderStatus;
import com.xinyunlian.jinfu.dealer.service.DealerUserOrderService;
import com.xinyunlian.jinfu.depository.dto.LoanDepositoryAcctDto;
import com.xinyunlian.jinfu.depository.service.LoanDepositoryAcctService;
import com.xinyunlian.jinfu.dto.PayRecvReqDto;
import com.xinyunlian.jinfu.dto.PayRecvResultDto;
import com.xinyunlian.jinfu.enums.PayRecvResult;
import com.xinyunlian.jinfu.external.dto.LoanApplOutBankCardDto;
import com.xinyunlian.jinfu.external.enums.ConfirmationType;
import com.xinyunlian.jinfu.external.service.ExternalService;
import com.xinyunlian.jinfu.external.service.LoanApplOutBankCardService;
import com.xinyunlian.jinfu.house.dto.UserHouseDto;
import com.xinyunlian.jinfu.house.service.UserHouseService;
import com.xinyunlian.jinfu.loan.dto.LoanApplUserDto;
import com.xinyunlian.jinfu.loan.dto.RepayDtlDto;
import com.xinyunlian.jinfu.loan.dto.rec.RecUserDto;
import com.xinyunlian.jinfu.loan.dto.req.LoanPayReq;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.enums.ERepayMode;
import com.xinyunlian.jinfu.loan.enums.ETransferStat;
import com.xinyunlian.jinfu.log.dto.LoanAuditLogDto;
import com.xinyunlian.jinfu.log.enums.ELoanAuditLogType;
import com.xinyunlian.jinfu.log.service.LoanAuditLogService;
import com.xinyunlian.jinfu.mq.DestinationDefine;
import com.xinyunlian.jinfu.mq.sender.QueueSender;
import com.xinyunlian.jinfu.pay.dto.PayRecvOrdDto;
import com.xinyunlian.jinfu.pay.enums.EOrdStatus;
import com.xinyunlian.jinfu.pay.enums.EPrType;
import com.xinyunlian.jinfu.pay.service.PayRecvOrdService;
import com.xinyunlian.jinfu.picture.dto.PictureDto;
import com.xinyunlian.jinfu.picture.enums.EPictureType;
import com.xinyunlian.jinfu.picture.service.PictureService;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.promo.dto.PromoDto;
import com.xinyunlian.jinfu.risk.dto.resp.UserCreditInfoDto;
import com.xinyunlian.jinfu.risk.service.UserCreditService;
import com.xinyunlian.jinfu.router.dto.FinanceSourceDto;
import com.xinyunlian.jinfu.router.service.FinanceSourceService;
import com.xinyunlian.jinfu.schedule.service.ScheduleService;
import com.xinyunlian.jinfu.spider.dto.resp.RiskUserInfoDto;
import com.xinyunlian.jinfu.spider.service.RiskUserInfoService;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.user.dto.*;
import com.xinyunlian.jinfu.user.service.UserService;
import com.xinyunlian.jinfu.user.dto.ext.UserExtAllDto;
import com.xinyunlian.jinfu.user.service.*;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by JL on 2016/9/29.
 */
@Service
public class PrivateLoanService {
    @Autowired
    private LoanService loanService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private PayRecvOrdService payRecvOrdService;

    @Autowired
    private UserService userService;

    @Autowired
    private PrivatePromoService privatePromoService;

    @Autowired
    private DealerUserOrderService dealerUserOrderService;

    @Autowired
    private UserExtService userExtService;

    @Autowired
    private UserLinkmanService userLinkmanService;

    @Autowired
    private StoreService storeService;

    @Autowired
    private UserHouseService userHouseService;

    @Autowired
    private UserCarService userCarService;

    @Autowired
    private LoanApplUserService loanApplUserService;

    @Autowired
    private UserBankAcctService userBankAcctService;

    @Autowired
    private RiskUserInfoService riskUserInfoService;

    @Autowired
    private UserCreditService userCreditService;

    @Autowired
    private LoanApplService loanApplService;

    @Autowired
    private BankService bankService;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private PrivateContractService privateContractService;

    @Autowired
    private QueueSender queueSender;

    @Autowired
    private FinanceSourceService financeSourceService;

    @Autowired
    private ExternalService externalService;

    @Autowired
    private LoanApplOutBankCardService loanApplOutBankCardService;

    @Autowired
    private LoanUserCreditLineService loanUserCreditLineService;

    @Autowired
    private DealerUserService dealerUserService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PrivateLoanService.class);

    /**
     * 获得用户默认推荐人信息
     *
     * @param userId
     * @return
     */
    public RecUserDto getRecUser(String userId) {

        RecUserDto recUserDto = new RecUserDto();

        recUserDto.setRecName("");
        recUserDto.setRecUser("");

        UserInfoDto userInfoDto = userService.findUserByUserId(userId);

        if (StringUtils.isNotEmpty(userInfoDto.getDealerUserId())) {
            DealerUserDto dealerUserDto = dealerUserService.getDealerUserById(userInfoDto.getDealerUserId());
            if (dealerUserDto != null) {
                recUserDto.setRecUser(dealerUserDto.getMobile());
                recUserDto.setRecName(dealerUserDto.getName());
            }
        }

        return recUserDto;
    }

    /**
     * 同意使用贷款
     *
     * @param applId
     * @param bankCardId
     */
    public void agree(String applId, Long bankCardId) throws BizServiceException {

        String userId = SecurityContext.getCurrentUserId();

        //获取产品信息
        LoanProductDetailDto product = loanApplService.getProduct(applId);

        //如果是爱投资贷款，同意使用时要做特殊判定
        LoanApplDto loanApplDto = loanApplService.get(applId);
        FinanceSourceDto financeSourceDto = financeSourceService.findById(loanApplDto.getFinanceSourceId());

        switch (financeSourceDto.getType()) {
            case OWN:
                this.doAgreeOwn(userId, this.doAgree(applId, bankCardId, product), product);
                break;
            case AITOUZI:
                //调用爱投资确认接口
                try {
                    this.doAgreeATZ(applId);
                } catch (Exception e) {
                    LOGGER.error("atz confirm error", e);
                    throw new BizServiceException(EErrorCode.LOAN_ATZ_BANKCARD_NEEDSURE, "确认使用贷款异常");
                }

                this.doAgree(applId, bankCardId, product);

                LoanApplOutBankCardDto loanApplOutBankCardDto = loanApplOutBankCardService.findByApplId(applId);
                if (loanApplOutBankCardDto == null) {
                    LoanApplOutBankCardDto latest = loanApplOutBankCardService.findByUserLatest(loanApplDto.getUserId());
                    if (latest == null) {
                        throw new BizServiceException(EErrorCode.LOAN_ATZ_NO_BANKCARD, "爱投资银行卡尚未同步，无法同意使用");
                    }
                    loanApplOutBankCardDto = new LoanApplOutBankCardDto();
                    loanApplOutBankCardDto.setApplId(applId);
                    loanApplOutBankCardDto.setUserId(loanApplDto.getUserId());
                    loanApplOutBankCardDto.setBankCardNo(latest.getBankCardNo());
                    loanApplOutBankCardService.save(loanApplOutBankCardDto);
                }


                break;
        }


        //通知变化到分销平台
        this.notifyUpdateToDealer(applId);
    }

    private void doAgreeOwn(String userId, LoanDtlDto loanDtlDto, LoanProductDetailDto product) throws BizServiceException {
        UserInfoDto userInfoDto = userService.findUserByUserId(userId);

        BankCardDto bankCardDto = bankService.getBankCard(loanDtlDto.getBankCardId());

        LoanPayReq loanPayReq = ConverterService.convert(bankCardDto, LoanPayReq.class);
        loanPayReq.setLoanId(loanDtlDto.getLoanId());
        loanPayReq.setUserMobile(userInfoDto.getMobile());
        loanPayReq.setBankCardMobile(bankCardDto.getMobileNo());
        loanPayReq.setLoanDtlDto(loanDtlDto);
        loanPayReq.setPrType(EPrType.PAY);
        loanPayReq.setLoanDtlDto(loanDtlDto);
        loanService.pay(loanPayReq);

            //签署代扣协议
        privateContractService.signDk(userInfoDto, loanDtlDto, product);
    }

    private void doAgreeATZ(String applId) {
        externalService.loanContractConfirm(applId, ConfirmationType.agree);
    }

    /**
     * 同意使用贷款
     *
     * @param applId     申请编号
     * @param bankCardId 收款卡ID
     * @param product    产品信息
     * @return
     */
    private LoanDtlDto doAgree(String applId, Long bankCardId, LoanProductDetailDto product) {
        String userId = SecurityContext.getCurrentUserId();

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("doAgree userId:{}", userId);
            LOGGER.debug("doAgree userId2:{}", SecurityContext.getCurrentUser().getUserId());
        }
        UserInfoDto userInfoDto = userService.findUserByUserId(SecurityContext.getCurrentUser().getUserId());

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("doAgree userInfoDto:{}", userInfoDto);
        }

        PromoDto promoDto = privatePromoService.gain(userInfoDto, applId);
        LOGGER.info("applId:{},promoId:{}", applId, promoDto != null ? promoDto.getPromoId() : "");

        //同意使用贷款，占用保留，更新
        LoanDtlDto loanDtlDto = loanService.agree(userId, applId, bankCardId, promoDto, product);

        return loanDtlDto;
    }


    public String callback(HttpServletRequest request, Boolean isDepository) {
        String outTradeNo = this.getRequestParam(request, "outTradeNo");
        String resultCode = this.getRequestParam(request, "resultCode");
        String resultMessage = this.xssDecoder(this.getRequestParam(request, "resultMessage"));
        String channelCode = this.getRequestParam(request, "channelCode");
        String channelName = this.getRequestParam(request, "channelName");

        Map<String, String> responseMap = new HashMap<>();
        Enumeration em = request.getParameterNames();
        while (em.hasMoreElements()) {
            String name = (String) em.nextElement();
            String value = this.xssDecoder(request.getParameter(name));
            responseMap.put(name, value);
        }

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("loan callback params,{}", this.getMapString(responseMap));
        }

        if (!isDepository) {
            if (!JinfuCashierSignature.verifySign(responseMap, AppConfigUtil.getConfig("cashier.pay.key"), true)) {
                LOGGER.error("放款验签失败");
                return "FAILED";
            }
        }


        try {
            PayRecvOrdDto payRecvOrdDto = payRecvOrdService.get(outTradeNo);
            LoanDtlDto loanDtlDto = loanService.get(payRecvOrdDto.getBizId());

            //找到银行卡
            BankCardDto bankCardDto = bankService.getBankCard(loanDtlDto.getBankCardId());
            //找到手机号
            UserInfoDto userInfoDto = userService.findUserByUserId(loanDtlDto.getUserId());

            ProcessRetDto processRetDto = new ProcessRetDto();
            processRetDto.setDesc(resultCode);
            processRetDto.setRetCode(resultCode);
            processRetDto.setRetMsg(resultMessage);
            payRecvOrdDto = this.completeReturnResult(payRecvOrdDto, processRetDto);
            payRecvOrdDto.setRetChannelCode(channelCode);
            payRecvOrdDto.setRetChannelName(channelName);

            loanDtlDto = loanService.payCallback(loanDtlDto, payRecvOrdDto);
            if (loanDtlDto.getTransferStat() == ETransferStat.SUCCESS) {
                queueSender.send(DestinationDefine.DEALER_SERVICE_FEE_DEDUCTION, loanDtlDto.getLoanId());
                scheduleService.generate(loanDtlDto.getUserId(), loanDtlDto.getApplId());
                loanService.sendPayOkMessage(loanDtlDto, bankCardDto.getBankCardNo(), userInfoDto.getMobile());
                if ("B".equals(loanDtlDto.getTestSource())) {
                    loanUserCreditLineService.use(loanDtlDto.getUserId());
                }
            }
            return "SUCCESS";
        } catch (Exception e) {
            LOGGER.error("放款异常", e);
            return "FAILED";
        }
    }

    private PayRecvOrdDto completeReturnResult(PayRecvOrdDto payRecvOrdDto, ProcessRetDto processRetDto) {
        if (processRetDto == null) {
            return payRecvOrdDto;
        }
        if (StringUtils.isEmpty(processRetDto.getDesc())) {
            LOGGER.error("processRetDto desc is null");
            return payRecvOrdDto;
        }
        Map<String, EOrdStatus> statusMap = new HashMap<>();
        statusMap.put("PROCESS", EOrdStatus.PROCESS);
        statusMap.put("SUCCESS", EOrdStatus.SUCCESS);
        statusMap.put("FAILED", EOrdStatus.FAILED);
        EOrdStatus status = statusMap.get(processRetDto.getDesc());

        payRecvOrdDto.setOrdStatus(status != null ? status : EOrdStatus.PROCESS);
        payRecvOrdDto.setRetCode(processRetDto.getRetCode());
        payRecvOrdDto.setRetMsg(processRetDto.getRetMsg());

        return payRecvOrdDto;
    }

    /**
     * 贷款单状态变化通知到分销平台
     *
     * @param applId
     */
    public void notifyUpdateToDealer(String applId) {
        try {
            DealerUserOrderDto dealerUserOrderDto = new DealerUserOrderDto();
            dealerUserOrderDto.setOrderNo(applId);
            dealerUserOrderDto.setStatus(EDealerUserOrderStatus.SUCCESS);
            dealerUserOrderService.updateOrderStatus(dealerUserOrderDto);
        } catch (Exception e) {
            LOGGER.warn("update loan status notify dealer has exception", e);
        }
    }

    /**
     * 签约成功之后的调用
     *
     * @param applId
     */
    public void updateSigned(String applId) {
        loanApplService.updateSigned(applId);
    }

    @Async
    public void saveLoanApplUser(String applId) {
        LOGGER.info("init apply user start,appl_id:{}", applId);

        LoanApplDto apply = loanApplService.get(applId);
        if (apply == null) {
            return;
        }

        LoanApplUserDto loanApplUserDto = loanApplUserService.findByApplId(applId);
        if (loanApplUserDto == null) {
            loanApplUserDto = new LoanApplUserDto();
        }

        loanApplUserDto.setApplId(applId);

        UserExtAllDto userExtAllDto = new UserExtAllDto();
        userExtAllDto.setUserId(apply.getUserId());
        userExtAllDto = (UserExtAllDto) userExtService.getUserExtPart(userExtAllDto);
        loanApplUserDto.setUserBase(JsonUtil.toJson(userExtAllDto));

        List<UserLinkmanDto> linkmans = userLinkmanService.findByUserId(apply.getUserId());
        loanApplUserDto.setUserLinkman(JsonUtil.toJson(linkmans));

        List<StoreInfDto> stores = storeService.findByUserId(apply.getUserId());
        loanApplUserDto.setStoreBase(JsonUtil.toJson(stores));

        //房产
        List<UserHouseDto> userHouseDtos = userHouseService.list(apply.getUserId());
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("apply snapshot house, appl_id:{}, userHouseDtos:{}", apply.getApplId(), userHouseDtos);
        }
        if (!CollectionUtils.isEmpty(userHouseDtos)) {
            userHouseDtos.forEach(userHouseDto -> {
                List<PictureDto> pictureDtos = pictureService.list(userHouseDto.getId().toString(), EPictureType.HOUSE_PROPERTY);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("apply snapshot house picture, house_id:{}, pictureDtos:{}", userHouseDto.getId(), pictureDtos);
                }
                userHouseDto.setPictureDtos(pictureDtos);
            });
        }
        loanApplUserDto.setHouseBase(JsonUtil.toJson(userHouseDtos));

        //车辆
        List<UserCarDto> userCarDtos = userCarService.list(apply.getUserId());
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("apply snapshot cars, appl_id:{}, userCarDtos:{}", apply.getApplId(), userCarDtos);
        }
        if (!CollectionUtils.isEmpty(userCarDtos)) {
            userCarDtos.forEach(userCarDto -> {
                List<PictureDto> pictureDtos = pictureService.list(userCarDto.getId().toString());
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("apply snapshot cars picture, car_id:{}, pictureDtos:{}", userCarDto.getId(), pictureDtos);
                }
                pictureDtos.forEach(item -> {
                    if (Arrays.asList(EPictureType.CAR_DRIVING_LICENSE, EPictureType.CAR_REGISTER_CERTIFICATE).contains(item.getPictureType())) {
                        userCarDto.getPictureDtos().add(item);
                    }
                });
            });
        }
        loanApplUserDto.setCarBase(JsonUtil.toJson(userCarDtos));

        //银行流水
        List<UserBankAcctDto> userBankAcctDtos = userBankAcctService.list(apply.getUserId());
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("apply snapshot banks, appl_id:{}, userCarDtos:{}", apply.getApplId(), userBankAcctDtos);
        }
        if (!CollectionUtils.isEmpty(userBankAcctDtos)) {
            userBankAcctDtos.forEach(userBankAcctDto -> {
                List<PictureDto> pictureDtos = pictureService.list(userBankAcctDto.getBankAccountId().toString(), EPictureType.BANK_TRADE);
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("apply snapshot cars picture, bank_id:{}, pictureDtos:{}", userBankAcctDto.getBankAccountId(), pictureDtos);
                }
                userBankAcctDto.setPictureDtos(pictureDtos);
            });
        }

        loanApplUserDto.setBankBase(JsonUtil.toJson(userBankAcctDtos));

        try {
            RiskUserInfoDto riskUserInfoDto = riskUserInfoService.findByUserId(apply.getUserId());
            loanApplUserDto.setUserTobacco(JsonUtil.toJson(riskUserInfoDto));
        } catch (Exception e) {
            LOGGER.error("spider tobacco occur exception", e);
        }

        UserCreditInfoDto userCredit = userCreditService.getUserCreditInfo(apply.getUserId());

        if (userCredit != null) {
            loanApplUserDto.setCreditPhone(userCredit.getPhoneInfo());
            loanApplUserDto.setCreditOverdue(userCredit.getOverDueInfo());
            loanApplUserDto.setCreditLoan(userCredit.getLoanInfo());
            loanApplUserDto.setCreditBlacklist(userCredit.getBlacklistInfo());
            loanApplUserDto.setCreditStatistics(userCredit.getLogStatitics());
        }

        loanApplUserService.save(loanApplUserDto);

        LOGGER.info("init apply user finish,appl_id:{}", applId);
    }

    public String xssDecoder(String str) {
        return StringEscapeUtils.unescapeHtml(str);
    }

    public String roundTwo(String amt) {
        if (StringUtils.isEmpty(amt)) {
            return amt;
        }
        try {
            return NumberUtil.roundTwo(new BigDecimal(amt)).toString();
        } catch (Exception e) {
            LOGGER.error("收银台回调金额转换异常:" + amt, e);
        }
        return amt;
    }

    /**
     * 从请求参数中获取，并且进行转码
     *
     * @param request
     * @param name
     * @return
     */
    public String getRequestParam(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        if (value == null) {
            LOGGER.error("request参数为null,{}", name);
            return value;
        }
        return value;
    }

    private String getMapString(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            sb.append("key:")
                    .append(entry.getKey())
                    .append(",value:")
                    .append(entry.getValue());
        }
        return sb.toString();
    }

}
