package com.xinyunlian.jinfu.loan.service;

import com.xinyunlian.jinfu.appl.dto.LoanApplMqDto;
import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.MaskUtil;
import com.xinyunlian.jinfu.common.util.NumberUtil;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import com.xinyunlian.jinfu.creditline.dto.LoanUserCreditLineDto;
import com.xinyunlian.jinfu.creditline.enums.ELoanUserCreditLineStatus;
import com.xinyunlian.jinfu.creditline.service.LoanUserCreditLineService;
import com.xinyunlian.jinfu.dealer.dto.DealerUserOrderDto;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserOrderSource;
import com.xinyunlian.jinfu.dealer.service.DealerService;
import com.xinyunlian.jinfu.dealer.service.DealerUserOrderService;
import com.xinyunlian.jinfu.external.dto.LoanApplOutBankCardDto;
import com.xinyunlian.jinfu.external.service.LoanApplOutBankCardService;
import com.xinyunlian.jinfu.loan.dto.bestsign.LoanBestSignDto;
import com.xinyunlian.jinfu.loan.dto.btest.BTestApplDto;
import com.xinyunlian.jinfu.loan.dto.btest.BTestRepayMode;
import com.xinyunlian.jinfu.loan.dto.rec.RecUserDto;
import com.xinyunlian.jinfu.loan.dto.req.LoanCustomerApplDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.dto.resp.apply.LoanApplyDetailCusDto;
import com.xinyunlian.jinfu.loan.dto.resp.apply.LoanBankDto;
import com.xinyunlian.jinfu.loan.enums.EApplChannel;
import com.xinyunlian.jinfu.loan.enums.ERepayMode;
import com.xinyunlian.jinfu.log.dto.LoanAuditLogDto;
import com.xinyunlian.jinfu.log.enums.ELoanAuditLogType;
import com.xinyunlian.jinfu.log.service.LoanAuditLogService;
import com.xinyunlian.jinfu.mq.DestinationDefine;
import com.xinyunlian.jinfu.mq.sender.QueueSender;
import com.xinyunlian.jinfu.mq.sender.TopicSender;
import com.xinyunlian.jinfu.prod.dto.ProductDto;
import com.xinyunlian.jinfu.prod.service.ProdService;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.product.service.LoanProductService;
import com.xinyunlian.jinfu.promo.dto.PromoDto;
import com.xinyunlian.jinfu.promo.dto.PromoInfDto;
import com.xinyunlian.jinfu.promo.dto.PromotionDto;
import com.xinyunlian.jinfu.promo.service.LoanPromoService;
import com.xinyunlian.jinfu.promo.service.PromotionService;
import com.xinyunlian.jinfu.router.dto.FinanceSourceDto;
import com.xinyunlian.jinfu.router.service.FinanceSourceService;
import com.xinyunlian.jinfu.spider.dto.req.AuthLoginDto;
import com.xinyunlian.jinfu.spider.service.RiskUserInfoService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Willwang on 2017/1/6.
 */
@Service
public class PrivateApplService {

    @Autowired
    private RiskUserInfoService riskUserInfoService;

    @Autowired
    private DealerUserOrderService dealerUserOrderService;

    @Autowired
    private DealerService dealerService;

    @Autowired
    private LoanProductService loanProductService;

    @Autowired
    private LoanApplService loanApplService;

    @Autowired
    private LoanPromoService loanPromoService;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private BankService bankService;

    @Autowired
    private LoanService loanService;

    @Autowired
    private PrivateContractService privateContractService;

    @Autowired
    private LoanAuditLogService loanAuditLogService;

    @Autowired
    private UserService userService;

    @Autowired
    private QueueSender queueSender;

    @Autowired
    private TopicSender topicSender;

    @Autowired
    private PrivateLoanService privateLoanService;

    @Autowired
    private PrivateApplService privateApplService;

    @Autowired
    private FinanceSourceService financeSourceService;

    @Autowired
    private LoanApplOutBankCardService outBankCardService;

    @Autowired
    private LoanUserCreditLineService loanUserCreditLineService;

    @Autowired
    private ProdService prodService;

    @Autowired
    private PrivateBestSignService privateBestSignService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PrivateApplService.class);

    /**
     * 用户新商盟数据爬取
     *
     * @param userId
     */
    public void checkSpiderCanLoginOrTimeout(String userId) {

        AuthLoginDto authLoginDto = new AuthLoginDto();
        authLoginDto.setUserId(userId);

        boolean canLoginOrTimeout = true;

        if (!AppConfigUtil.isDevEnv()) {
            try {
                boolean rs = riskUserInfoService.authLogin(authLoginDto);
                if (!rs) {
                    canLoginOrTimeout = false;
                }
            } catch (Exception e) {
                LOGGER.info(String.format("user %s xinshangmeng login error, msg:%s", authLoginDto.getUserId(), e.getMessage()));
            }

            if (!canLoginOrTimeout) {
                throw new BizServiceException(EErrorCode.LOAN_APPL_XINSHANGMENG_AUTH_ERROR);
            }
        }
    }

    /**
     * 用户填写了推荐人信息的，通知给分销平台
     *
     * @param recUser
     * @param userId
     * @param applId
     * @param productId
     */
    public DealerUserOrderDto notifyDealer(String recUser, String userId, String applId, String productId) {
        DealerUserOrderDto rs = null;
        try {
            if (StringUtils.isNotEmpty(recUser)) {
                DealerUserOrderDto dealerUserOrderDto = new DealerUserOrderDto();
                dealerUserOrderDto.setStoreUserId(userId);
                dealerUserOrderDto.setOrderNo(applId);
                dealerUserOrderDto.setMobile(recUser);
                dealerUserOrderDto.setProdId(productId);
                dealerUserOrderDto.setSource(EDealerUserOrderSource.LOAN);
                rs = dealerUserOrderService.createDealerUserOrder(dealerUserOrderDto);
            }
        } catch (Exception e) {
            LOGGER.warn("user start apply notify dealer has exception", e);
        }
        return rs;
    }

    /**
     * 贷款详情数据补全活动信息
     *
     * @param detail
     * @return
     */
    public LoanApplyDetailCusDto completePromo(LoanApplyDetailCusDto detail) {
        if (StringUtils.isNotBlank(detail.getLoanId())) {
            PromoDto promoDto = loanPromoService.get(detail.getLoanId());
            if (null != promoDto) {
                PromotionDto promotionDto = promotionService.getByPromotionId(promoDto.getPromoId());
                if (null != promotionDto) {
                    if (null != promotionDto.getPromoInfDto()) {
                        PromoInfDto promoInfDto = promotionDto.getPromoInfDto();
                        detail.setPromoId(promoInfDto.getPromoId());
                        detail.setPromoName(promoInfDto.getAlias());
                        detail.setPromoDesc(promoInfDto.getDescribe());
                    }
                }
            }
        }

        return detail;
    }

    /**
     * 补全银行卡信息
     *
     * @param detail
     * @return
     */
    public LoanApplyDetailCusDto completeBank(LoanApplyDetailCusDto detail) {

        LoanApplDto loanApplDto = loanApplService.get(detail.getApplId());

        if (loanApplDto.getFinanceSourceId() == null) {
            return detail;
        }


        FinanceSourceDto financeSourceDto = financeSourceService.findById(loanApplDto.getFinanceSourceId());

        if (financeSourceDto == null) {
            return detail;
        }

        switch (financeSourceDto.getType()) {
            case OWN:
                if (StringUtils.isNotBlank(detail.getLoanId())) {
                    LoanDtlDto loan = loanService.get(detail.getLoanId());

                    if (loan != null && loan.getBankCardId() != null) {
                        BankCardDto bankCardDto = bankService.getBankCard(loan.getBankCardId());
                        if (bankCardDto != null) {
                            LoanBankDto bank = new LoanBankDto();
                            bank.setBankCode(bankCardDto.getBankCode());
                            bank.setBankName(bankCardDto.getBankName());
                            bank.setLastNo(MaskUtil.getLastFour(bankCardDto.getBankCardNo()));
                            bank.setLogo(bankCardDto.getBankLogo());
                            detail.setBank(bank);
                        }
                    }
                }
                break;
            case AITOUZI:
                LoanApplOutBankCardDto loanApplOutBankCardDto = outBankCardService.findByApplId(loanApplDto.getApplId());
                if (loanApplOutBankCardDto != null) {
                    detail.setBank(this.convertToBankDto(loanApplOutBankCardDto));
                } else {
                    loanApplOutBankCardDto = outBankCardService.findByUserLatest(loanApplDto.getUserId());
                    if (loanApplOutBankCardDto != null) {
                        detail.setBank(this.convertToBankDto(loanApplOutBankCardDto));
                    }
                }
                break;
            default:
                break;
        }

        return detail;
    }

    private LoanBankDto convertToBankDto(LoanApplOutBankCardDto loanApplOutBankCardDto) {
        LoanBankDto bank = new LoanBankDto();
        bank.setLastNo(MaskUtil.getLastFour(loanApplOutBankCardDto.getBankCardNo()));
        bank.setBankName("");
        bank.setBankCode("");
        bank.setLogo("");
        return bank;
    }

    /**
     * 申请贷款
     *
     * @param userId
     * @param channel
     * @param applDto
     * @return
     */
    public String start(String userId, EApplChannel channel, LoanCustomerApplDto applDto) {
        LoanProductDetailDto product = loanProductService.getProdDtl(applDto.getProductId());
        applDto.setProduct(product);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("apply start begin check,{}", System.currentTimeMillis());
        }

        //是否可以申请贷款
        loanApplService.checkStart(userId, applDto);

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("apply start end check,{}", System.currentTimeMillis());
        }

        //爬虫是否成功抓取到用户新商盟信息
        this.checkSpiderCanLoginOrTimeout(userId);

        //签署征信授权协议
        privateContractService.signZxsq();

        //申请渠道判定
        if (channel == null && applDto.getChannel() == null) {
            channel = EApplChannel.SELF;
        }
        if (channel != null) {
            applDto.setChannel(channel);
        }
        applDto.setChannel(channel);
        //分销商服务费
        BigDecimal dealerServiceFeeRt = BigDecimal.ZERO;
        if (product.getRepayMode() == ERepayMode.MONTH_AVE_CAP_AVG_INTR) {
            dealerServiceFeeRt = dealerService.getDealerFeeRt(applDto.getRecUser());
            if (dealerServiceFeeRt == null) {
                dealerServiceFeeRt = BigDecimal.ZERO;
            }
        }
        applDto.setDealerServiceFeeRt(dealerServiceFeeRt);
        //申请
        LoanApplDto loanApplDto = loanApplService.apply(userId, applDto);

        this.afterApply(applDto, loanApplDto);

        return loanApplDto.getApplId();
    }

    /**
     * 贷款申请后续处理
     *
     * @param applDto
     * @param loanApplDto
     */
    private void afterApply(LoanCustomerApplDto applDto, LoanApplDto loanApplDto) {
        //小贷订单通知给分销平台
        DealerUserOrderDto dealerUserOrderDto = this.notifyDealer(
                applDto.getRecUser(), loanApplDto.getUserId(), loanApplDto.getApplId(), applDto.getProductId()
        );

        //如果存在分销商推荐信息，更新到appl表
        if (dealerUserOrderDto != null) {
            loanApplDto.setDealerId(dealerUserOrderDto.getDealerId());
            loanApplDto.setDealerUserId(dealerUserOrderDto.getUserId());
            loanApplService.save(loanApplDto);

            //通知用户服务进行更新
            userService.updateDealerUserId(loanApplDto.getUserId(), loanApplDto.getDealerUserId());
        }

        //通知到日志
        this.saveAuditLog(loanApplDto, ELoanAuditLogType.APPL);

        LoanApplMqDto mqDto = ConverterService.convert(loanApplDto, LoanApplMqDto.class);
        String mqDtoString = JsonUtil.toJson(mqDto);
        //通知给征信服务
        topicSender.send(DestinationDefine.LOAN_APPLY_POST_HANDLE, mqDtoString);
        //通知给发券服务
        queueSender.send(DestinationDefine.LOAN_APPLY_PROMO_COUPON, mqDtoString);

        //爬取授权信息
        riskUserInfoService.spiderUserInfo(loanApplDto.getUserId());

        //做快照数据
        privateLoanService.saveLoanApplUser(loanApplDto.getApplId());
    }

    /**
     * 重新发起贷款
     *
     * @param applId
     */
    public void restart(String applId) {
        String userId = SecurityContext.getCurrentUserId();
        LoanApplDto loanApplDto = loanApplService.restart(userId, applId);

        LoanApplMqDto mqDto = ConverterService.convert(loanApplDto, LoanApplMqDto.class);
        String mqDtoString = JsonUtil.toJson(mqDto);
        //通知给征信服务
        topicSender.send(DestinationDefine.LOAN_APPLY_POST_HANDLE, mqDtoString);

        privateApplService.saveAuditLog(loanApplDto, ELoanAuditLogType.APPL_RETRY);

        privateLoanService.saveLoanApplUser(loanApplDto.getApplId());
    }

    /**
     * BTest贷款申请页面
     *
     * @return
     */
    public BTestApplDto bTest() {

        String userId = SecurityContext.getCurrentUserId();

        BTestApplDto bTestApplDto = new BTestApplDto();

        //额度
        bTestApplDto = this.bTestCreditLine(userId, bTestApplDto);

        //银行卡
        bTestApplDto = this.bTestBank(userId, bTestApplDto);

        //还款方式数据
        bTestApplDto = this.bTestRepayModes(userId, bTestApplDto);

        RecUserDto recUserDto = privateLoanService.getRecUser(userId);
        ConverterService.convert(recUserDto, bTestApplDto);

        return bTestApplDto;
    }

    /**
     * BTest贷款申请
     */
    public LoanBestSignDto bTestStart(LoanCustomerApplDto applDto) {

        String userId = SecurityContext.getCurrentUserId();

        LoanProductDetailDto product = loanProductService.getProdDtl(applDto.getProductId());
        applDto.setProduct(product);

        LoanApplDto loanApplDto = loanApplService.bTestStart(userId, applDto);

        this.afterApply(applDto, loanApplDto);

        return this.getContract(loanApplDto);
    }

    private LoanBestSignDto getContract(LoanApplDto loanApplDto) {

        ECntrctTmpltType type = null;

        if ("L01001".equals(loanApplDto.getProductId())) {
            type = ECntrctTmpltType.YDL01001;
        } else if ("L01002".equals(loanApplDto.getProductId())) {
            type = ECntrctTmpltType.YDL01002;
        }

        LOGGER.debug("get contract type:{}", type == null ? "NULL" : type.getCode());

        privateBestSignService.setSealName("");
        privateBestSignService.setReturnUrl("/jinfu/web/loan/signedContract");

        return privateBestSignService.get(loanApplDto.getApplId(), type);
    }

    private BTestApplDto bTestCreditLine(String userId, BTestApplDto bTestApplDto) {

        LoanUserCreditLineDto loanUserCreditLineDto = loanUserCreditLineService.get(userId);

        if (loanUserCreditLineDto == null || loanUserCreditLineDto.getStatus() != ELoanUserCreditLineStatus.UNUSED) {
            throw new BizServiceException(EErrorCode.TECH_DATA_INVALID, "额度无效");
        }

        bTestApplDto.setAmt(loanUserCreditLineDto.getAvailable());

        return bTestApplDto;
    }

    private BTestApplDto bTestBank(String userId, BTestApplDto bTestApplDto) {

        List<BankCardDto> bankCardDtos = bankService.findByUserId(userId);

        if (bankCardDtos.size() <= 0) {
            return bTestApplDto;
        }

        BankCardDto bankCardDto = bankCardDtos.get(0);

        LoanBankDto bank = new LoanBankDto();
        bank.setBankCardId(bankCardDto.getBankCardId());
        bank.setBankCode(bankCardDto.getBankCode());
        bank.setBankName(bankCardDto.getBankName());
        bank.setLastNo(MaskUtil.getLastFour(bankCardDto.getBankCardNo()));
        bank.setLogo(bankCardDto.getBankLogo());

        bTestApplDto.setBank(bank);

        return bTestApplDto;
    }

    private BTestApplDto bTestRepayModes(String userId, BTestApplDto bTestApplDto) {

        List<BTestRepayMode> repayModes = new ArrayList<>();

        String[] productIds = new String[]{"L01001", "L01002"};

        for (String productId : productIds) {

            LoanProductDetailDto product = loanProductService.getProdDtl(productId);

            BTestRepayMode repayMode = new BTestRepayMode();

            repayMode.setProdId(productId);
            repayMode.setName(product.getRepayMode().getAlias());
            repayMode.setRepayMode(product.getRepayMode());
            repayMode.setUnit(product.getTermType().getUnit());
            repayMode.setPeriods(this.split(product.getTermLen()));
            repayMode.setRate(product.getRepayMode().getRate(
                    product.getIntrRateType(), product.getIntrRate()
            ));
            repayMode = this.repayModeCustom(product, repayMode);

            repayModes.add(repayMode);
        }

        bTestApplDto.setRepayModes(repayModes);

        return bTestApplDto;
    }


    private BTestRepayMode repayModeCustom(LoanProductDetailDto product, BTestRepayMode repayMode) {

        ProductDto productDto = prodService.getProdById(product.getProductId());

        String customInterestType = "月利率";
        if (product.getRepayMode() == ERepayMode.INTR_PER_DIEM) {
            customInterestType = "日利率";
        }

        String customInterest =
                NumberUtil.roundTwo(product.getIntrRate().multiply(BigDecimal.valueOf(100))).toString() + "%";

        if (productDto != null && productDto.getProdAppDetailDto() != null) {

            String t1 = productDto.getProdAppDetailDto().getProdSubTitleLeft();
            String t2 = productDto.getProdAppDetailDto().getProdTitle();

            if (StringUtils.isNotEmpty(t1)) {
                customInterestType = t1;
            }

            if (StringUtils.isNotEmpty(t2)) {
                customInterest = t2;
            }

        }

        repayMode.setCustomInterestType(customInterestType);
        repayMode.setCustomInterest(customInterest);

        return repayMode;
    }

    private List<Integer> split(String termLen) {
        List<Integer> rs = new ArrayList<>();
        if (termLen.contains(",")) {
            List<String> list = Arrays.asList(termLen.split(","));
            for (String s : list) {
                rs.add(Integer.valueOf(s));
            }
        } else {
            rs.add(Integer.valueOf(termLen));
        }

        //逆序排列
        Collections.reverse(rs);

        return rs;
    }

    /**
     * 提交/重新提交申请的日志
     */
    public void saveAuditLog(LoanApplDto loanApplDto, ELoanAuditLogType auditLogType) {
        UserInfoDto user = userService.findUserByUserId(loanApplDto.getUserId());
        LoanAuditLogDto log = new LoanAuditLogDto(user.getUserName(), loanApplDto.getApplId(), auditLogType,
                user.getUserName(), loanApplDto.getApplId(), NumberUtil.roundTwo(loanApplDto.getApplAmt()).toString(),
                loanApplDto.getTermLen() + loanApplDto.getTermType().getUnit(), loanApplDto.getRepayMode().getAlias()
        );
        loanAuditLogService.save(log);
    }
}
