package com.xinyunlian.jinfu.yunma.controller;

import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.dto.CorpBankDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.bank.service.CorpBankService;
import com.xinyunlian.jinfu.cashier.consts.CashierConsts;
import com.xinyunlian.jinfu.cashier.dto.*;
import com.xinyunlian.jinfu.cashier.service.BankMappingService;
import com.xinyunlian.jinfu.cashier.service.CloudCodeService;
import com.xinyunlian.jinfu.cashier.service.PayForAnotherService;
import com.xinyunlian.jinfu.clerk.dto.ClerkAuthDto;
import com.xinyunlian.jinfu.clerk.dto.ClerkInfDto;
import com.xinyunlian.jinfu.clerk.service.ClerkAuthService;
import com.xinyunlian.jinfu.clerk.service.ClerkService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.entity.id.Context;
import com.xinyunlian.jinfu.common.entity.id.IdUtil;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.common.sms.SmsUtil;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.ApplicationContextUtil;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.RandomUtil;
import com.xinyunlian.jinfu.payCode.dto.PayCodeDto;
import com.xinyunlian.jinfu.payCode.dto.PayCodeSearchDto;
import com.xinyunlian.jinfu.payCode.enums.PayCodeBalanceType;
import com.xinyunlian.jinfu.payCode.service.PayCodeService;
import com.xinyunlian.jinfu.store.controller.StoreController;
import com.xinyunlian.jinfu.store.dto.MemberInfoDto;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.trade.dto.YmTradeDto;
import com.xinyunlian.jinfu.trade.enums.EBizCode;
import com.xinyunlian.jinfu.trade.enums.ETradeStatus;
import com.xinyunlian.jinfu.trade.enums.ETradeType;
import com.xinyunlian.jinfu.trade.service.YmTradeService;
import com.xinyunlian.jinfu.yunma.dto.*;
import com.xinyunlian.jinfu.yunma.enums.EMemberAuditStatus;
import com.xinyunlian.jinfu.yunma.enums.EMemberIntoStatus;
import com.xinyunlian.jinfu.yunma.enums.EMemberStatus;
import com.xinyunlian.jinfu.yunma.enums.EYmPayChannel;
import com.xinyunlian.jinfu.yunma.service.YMMemberService;
import com.xinyunlian.jinfu.yunma.service.YMUserInfoService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by menglei on 2017年08月31日.
 */
@Controller
@RequestMapping(value = "yunma/pay")
public class YmPayController {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(StoreController.class);

    @Autowired
    private YMUserInfoService yMUserInfoService;
    @Autowired
    private YMMemberService yMMemberService;
    @Autowired
    private BankService bankService;
    @Autowired
    private CloudCodeService cloudCodeService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private CorpBankService corpBankService;
    @Autowired
    private YmTradeService ymTradeService;
    @Autowired
    private PayForAnotherService payForAnotherService;
    @Autowired
    private BankMappingService bankMappingService;
    @Autowired
    private PayCodeService payCodeService;
    @Autowired
    private ClerkService clerkService;
    @Autowired
    private ClerkAuthService clerkAuthService;

    public static final String WECHAT_QRCODE_PREFIX = "13";//微信
    public static final String ALIPAY_QRCODE_PREFIX = "28";//支付宝
    public static final String PAY_QRCODE_PREFIX = "http";//支付码

    /**
     * 扫码收款
     *
     * @param merchantScanDto
     * @return
     */
    @RequestMapping(value = "/merchantscan", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("扫码收款")
    public ResultDto<Object> merchantscan(@RequestBody MerchantScanDto merchantScanDto, BindingResult result) throws InterruptedException {
        if (result.hasErrors()) {
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }

        if (merchantScanDto.getAmount().compareTo(BigDecimal.ZERO) == -1 || merchantScanDto.getAmount().compareTo(BigDecimal.ZERO) == 0) {
            return ResultDtoFactory.toNack("支付金额必须大于0");
        }
        YMMemberDto yMMemberDto = yMMemberService.getMemberByQrCodeNo(merchantScanDto.getQrCodeNo());
        if (yMMemberDto == null) {
            return ResultDtoFactory.toNack("该云码未绑定店铺");
        } else if (StringUtils.equals(yMMemberDto.getMemberStatus().getCode(), EMemberStatus.DISABLE.getCode())) {
            return ResultDtoFactory.toNack("云码已停用");
        } else if (!EMemberAuditStatus.SUCCESS.equals(yMMemberDto.getMemberAuditStatus())) {
            return ResultDtoFactory.toNack("该云码店铺审核中或审核不通过");
        } else if (!EMemberIntoStatus.INTO_SUCCESS.equals(yMMemberDto.getMemberIntoStatus())) {
            return ResultDtoFactory.toNack("该云码店铺进件中或进件失败");
        }
        StoreInfDto storeInfDto = storeService.findByStoreId(yMMemberDto.getStoreId());
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        YmMemberBizDto wechatBizDto = yMMemberService.getMemberBizByMemberNoAndBizCode(yMMemberDto.getMemberNo(), EBizCode.WECHAT);//费率
        YmMemberBizDto alipayBizDto = yMMemberService.getMemberBizByMemberNoAndBizCode(yMMemberDto.getMemberNo(), EBizCode.ALLIPAY);//费率
        if (wechatBizDto == null || alipayBizDto == null) {
            return ResultDtoFactory.toNack("未配置费率");
        }
        String bankCardNo = null;
        String acctName = null;
        String bankCode = null;
        String acctType = null;
        if (yMMemberDto.getBankCardId() != null) {
            BankCardDto bankCardDto = bankService.getBankCard(yMMemberDto.getBankCardId());//银行卡
            if (bankCardDto == null) {
                return ResultDtoFactory.toNack("未添加银行卡");
            }
            bankCardNo = bankCardDto.getBankCardNo();
            acctName = bankCardDto.getBankCardName();
            bankCode = bankCardDto.getBankCode();
            acctType = "0";
        } else if (yMMemberDto.getCorpBankId() != null) {
            CorpBankDto corpBankDto = corpBankService.getCorpBankById(yMMemberDto.getCorpBankId());//对公账号
            if (corpBankDto == null) {
                return ResultDtoFactory.toNack("未添加银行卡");
            }
            bankCardNo = corpBankDto.getAccount();
            acctName = corpBankDto.getAcctName();
            bankCode = corpBankDto.getBankShortName();
            acctType = "1";
        } else {
            return ResultDtoFactory.toNack("未添加银行卡");
        }
        //判断支付方式
        String walletType = null;
        if (StringUtils.startsWith(merchantScanDto.getPayCode(), ALIPAY_QRCODE_PREFIX)) {//支付宝
            walletType = CashierConsts.WalletType.WALLET_TYPE_ALIPAY;
        } else if (StringUtils.startsWith(merchantScanDto.getPayCode(), WECHAT_QRCODE_PREFIX)) {//微信
            walletType = CashierConsts.WalletType.WALLET_TYPE_WECHATPAY;
        } else if (StringUtils.startsWith(merchantScanDto.getPayCode(), PAY_QRCODE_PREFIX)) {//支付码
            walletType = null;
        }
        //支付
        //String orderNo = "8" + String.valueOf(System.currentTimeMillis()).substring(0, 10) + RandomUtil.getNumberStr(3);//生成订单号
        String orderNo = ymTradeService.getOrderNo();
        String ourTradeNo = null;
        ETradeStatus tradeStatus = ETradeStatus.UNPAID;
        //生成订单
        YmTradeDto ymTradeDto = new YmTradeDto();
        ymTradeDto.setTradeNo(orderNo);
        ymTradeDto.setMemberNo(yMMemberDto.getMemberNo());
        if (CashierConsts.WalletType.WALLET_TYPE_ALIPAY.equals(walletType)) {
            ymTradeDto.setBizCode(EBizCode.ALLIPAY);
            ymTradeDto.setTransFee(merchantScanDto.getAmount().multiply(alipayBizDto.getRate()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
        } else if (CashierConsts.WalletType.WALLET_TYPE_WECHATPAY.equals(walletType)) {
            ymTradeDto.setBizCode(EBizCode.WECHAT);
            ymTradeDto.setTransFee(merchantScanDto.getAmount().multiply(wechatBizDto.getRate()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
        } else {
            ymTradeDto.setBizCode(EBizCode.PAYCODE);
            ymTradeDto.setTransFee(merchantScanDto.getAmount().multiply(BigDecimal.valueOf(0.03)).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        ymTradeDto.setTransAmt(merchantScanDto.getAmount());
        ymTradeDto.setCardNo(bankCardNo);
        ymTradeDto.setStatus(ETradeStatus.UNPAID);
        ymTradeDto.setType(ETradeType.MERCHANT_SCAN);
        ymTradeDto = ymTradeService.saveTrade(ymTradeDto);
        //支付
        if (StringUtils.isNotEmpty(walletType)) {//微信，支付宝
            CloudCodePayOrderDto cloudCodePayOrderDto = new CloudCodePayOrderDto();
            cloudCodePayOrderDto.setPayQrCode(merchantScanDto.getPayCode());
            cloudCodePayOrderDto.setWalletAuthCode(merchantScanDto.getPayCode());
            cloudCodePayOrderDto.setPartnerId(AppConfigUtil.getConfig("cashier.partnerid"));
            cloudCodePayOrderDto.setMerchantId(yMMemberDto.getMemberNo());
            cloudCodePayOrderDto.setWalletType(walletType);//1alipay 2wechat
            cloudCodePayOrderDto.setSrcAmt(merchantScanDto.getAmount());//0.01
            cloudCodePayOrderDto.setDiscountAmt(BigDecimal.valueOf(0));
            cloudCodePayOrderDto.setGoodsDesc(storeInfDto.getStoreName());//storeName
            //cloudCodePayOrderDto.setDeviceId();
            cloudCodePayOrderDto.setNotifyUrl(AppConfigUtil.getConfig("cashier.notify.url"));//异步回调
            cloudCodePayOrderDto.setOutTradeNo(orderNo);
            cloudCodePayOrderDto.setUserId(StringUtils.EMPTY);
            cloudCodePayOrderDto.setAppId(AppConfigUtil.getConfig("cashier.appid"));
            MctScanPayResp mctScanPayResp;
            LOGGER.info("支付通扫码收款下单开始：" + JsonUtil.toJson(cloudCodePayOrderDto));
            try {
                mctScanPayResp = cloudCodeService.smartpayBSCPay(cloudCodePayOrderDto);
            } catch (Exception e) {
                return ResultDtoFactory.toNack("支付失败,请联系客服");
            }
            LOGGER.info("支付通扫码收款下单完成：" + JsonUtil.toJson(mctScanPayResp));
            ourTradeNo = mctScanPayResp.getOrderNo();
            if ("SUCCESS".equals(mctScanPayResp.getTxnStatus())) {
                tradeStatus = ETradeStatus.SUCCESS;
            } else if ("FAILED".equals(mctScanPayResp.getTxnStatus())) {
                tradeStatus = ETradeStatus.Fail;
            }
        } else {//支付码
            BankMappingDto bankMappingDto = bankMappingService.getBankMappingByShortName(bankCode);
            if (bankMappingDto == null) {
                return ResultDtoFactory.toNack("该银行卡不支持");
            }
            //扣钱
            PayCodeDto payCode = null;
            //获取paycode
            PayCodeSearchDto searchDto = new PayCodeSearchDto();
            searchDto.setPayCodeUrl(merchantScanDto.getPayCode());
            PayCodeSearchDto payCodeSearchDto = payCodeService.list(searchDto);
            if (CollectionUtils.isEmpty(payCodeSearchDto.getList())) {
                return ResultDtoFactory.toNack("支付码不存在");
            }
            String payCodeNo = payCodeSearchDto.getList().get(0).getPayCodeNo();
            LOGGER.info("支付码收款开始：" + payCodeNo);
            try {
                payCode = payCodeService.balance(payCodeNo, merchantScanDto.getAmount(), PayCodeBalanceType.PAY, orderNo);
                Map<String, String> params = new HashMap<>();
                params.put("totalAmt", String.valueOf(merchantScanDto.getAmount()));
                params.put("accountBalance", String.valueOf(payCode.getBalance()));
                SmsUtil.send("152", params, payCode.getMobile());
            } catch (Exception e) {
                return ResultDtoFactory.toNack(e.getMessage());
            }
            LOGGER.info("支付码收款结束：" + payCode);
            if (payCode == null) {
                return ResultDtoFactory.toNack("支付码扣款失败,请联系客服");
            }
            //打款
            PayForAnotherDto payForAnotherDto = new PayForAnotherDto();
            payForAnotherDto.setAppId(AppConfigUtil.getConfig("cashier.appid"));
            payForAnotherDto.setPartnerId(AppConfigUtil.getConfig("cashier.partnerid"));
            payForAnotherDto.setOrderNo(orderNo);
            payForAnotherDto.setAcctType(acctType);//账户类型 0对私 1对公
            payForAnotherDto.setBankCardNo(bankCardNo);//银行卡号
            payForAnotherDto.setAcctName(acctName);//户名
            payForAnotherDto.setBankName(bankMappingDto.getBankName());//银行名称
            payForAnotherDto.setAmount(String.valueOf(merchantScanDto.getAmount().subtract(ymTradeDto.getTransFee())));//金额
            payForAnotherDto.setNotifyUrl(AppConfigUtil.getConfig("cashier.notify.url"));//回调
            ProcessRetDto processRetDto;
            LOGGER.info("支付码打款开始：" + JsonUtil.toJson(payForAnotherDto));
            try {
                processRetDto = payForAnotherService.outerPayForAnother(payForAnotherDto);
            } catch (Exception e) {
                return ResultDtoFactory.toNack("支付码打款失败,请联系客服");
            }
            LOGGER.info("支付码打款结束：" + JsonUtil.toJson(processRetDto));
            ourTradeNo = processRetDto.getPayOrderNo();
            // 轮询等待
            Long startRollingWaiting = new Date().getTime();
            Thread.sleep(3000l);
            while ((new Date().getTime() - startRollingWaiting) < 60000) {//轮询一分钟
                YmTradeDto dto = ymTradeService.findByTradeNo(orderNo);
                if (dto != null && !ETradeStatus.UNPAID.equals(dto.getStatus())) {
                    tradeStatus = dto.getStatus();
                    break;
                }
            }
        }
        ymTradeDto.setOutTradeNo(ourTradeNo);
        ymTradeService.updateOutTradeNo(ymTradeDto);

        ymTradeDto = ymTradeService.findByTradeNo(orderNo);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("tradeNo", ymTradeDto.getTradeNo());
        resultMap.put("amount", String.valueOf(ymTradeDto.getTransAmt()));
        resultMap.put("type", ymTradeDto.getType().getCode());
        resultMap.put("status", tradeStatus.getCode());
        resultMap.put("creates", String.valueOf(ymTradeDto.getCreateTs()));
        resultMap.put("bizCode", ymTradeDto.getBizCode().getCode());

        LOGGER.info("扫码收款完成：" + JsonUtil.toJson(resultMap));
        return ResultDtoFactory.toAck("下单成功", resultMap);
    }

    @ResponseBody
    @RequestMapping(value = "/yunmaList", method = RequestMethod.GET)
    public ResultDto<Object> getYunmaList() {
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByYmUserId(SecurityContext.getCurrentUserId());
        if (yMUserInfoDto == null) {
            return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
        }
        List<YMMemberDto> members;
        if (StringUtils.isNotEmpty(yMUserInfoDto.getUserId())) {//店主
            members = yMMemberService.getMemberListByUserId(yMUserInfoDto.getUserId());//已绑云码店铺
        } else {
            ClerkInfDto clerkInfDto = clerkService.findByOpenId(yMUserInfoDto.getOpenId());
            if (clerkInfDto == null) {//店员
                return ResultDtoFactory.toNack("用户未绑定");//用户没有绑定微信，需先绑定微信
            }
            List<ClerkAuthDto> clerkAuthList = clerkAuthService.findByClerkId(clerkInfDto.getClerkId());
            if (clerkAuthList.isEmpty()) {
                return ResultDtoFactory.toAck("获取成功", null);
            }
            List<Long> storeIds = new ArrayList<>();
            for (ClerkAuthDto clerkAuthDto : clerkAuthList) {
                storeIds.add(Long.valueOf(clerkAuthDto.getStoreId()));
            }
            members = yMMemberService.getMemberByStoreIds(storeIds);
        }
        List<Long> storeIds = new ArrayList<>();
        for (YMMemberDto yMMemberDto : members) {
            storeIds.add(yMMemberDto.getStoreId());
        }
        if (CollectionUtils.isEmpty(storeIds)) {
            return ResultDtoFactory.toAck("获取成功", null);
        }
        List<StoreInfDto> storeList = storeService.findByStoreIds(storeIds);
        Map<Long, StoreInfDto> storeMap = new HashMap<>();
        for (StoreInfDto dto : storeList) {
            storeMap.put(dto.getStoreId(), dto);
        }
        List<MemberInfoDto> list = new ArrayList<>();
        for (YMMemberDto yMMemberDto : members) {
            if (storeMap.get(yMMemberDto.getStoreId()) != null) {
                StoreInfDto storeInfDto = storeMap.get(yMMemberDto.getStoreId());
                //支付通道选择
                List<MerchantSupportedChannelDetailDto> merchantSupportedChannelDtos = cloudCodeService.findMerchantChannelDetails(yMMemberDto.getMemberNo());
                for (MerchantSupportedChannelDetailDto merchantSupportedChannelDetailDto : merchantSupportedChannelDtos) {
                    if (EYmPayChannel.SMARTPAY.getCode().equals(merchantSupportedChannelDetailDto.getChannelProvider())
                            && merchantSupportedChannelDetailDto.getAlipay() && merchantSupportedChannelDetailDto.getWechat()) {
                        MemberInfoDto memberInfoDto = ConverterService.convert(yMMemberDto, MemberInfoDto.class);
                        memberInfoDto.setStoreName(storeInfDto.getStoreName());
                        list.add(memberInfoDto);
                    }
                }
            }
        }
        return ResultDtoFactory.toAck("获取成功", list);
    }

}
