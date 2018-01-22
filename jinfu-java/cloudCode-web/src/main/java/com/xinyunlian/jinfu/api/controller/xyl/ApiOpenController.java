package com.xinyunlian.jinfu.api.controller.xyl;

import com.xinyunlian.jinfu.api.dto.xyl.*;
import com.xinyunlian.jinfu.api.validate.OpenApi;
import com.xinyunlian.jinfu.api.validate.util.SignUtil;
import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.dto.CorpBankDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.bank.service.CorpBankService;
import com.xinyunlian.jinfu.cashier.consts.CashierConsts;
import com.xinyunlian.jinfu.cashier.dto.CloudCodePayOrderDto;
import com.xinyunlian.jinfu.cashier.dto.CloudCodePrepayOrderDto;
import com.xinyunlian.jinfu.cashier.dto.MctScanPayResp;
import com.xinyunlian.jinfu.cashier.service.CloudCodeService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.trade.dto.YmTradeDto;
import com.xinyunlian.jinfu.trade.enums.EBizCode;
import com.xinyunlian.jinfu.trade.enums.ETradeStatus;
import com.xinyunlian.jinfu.trade.enums.ETradeType;
import com.xinyunlian.jinfu.trade.service.YmTradeService;
import com.xinyunlian.jinfu.user.dto.UserInfoDto;
import com.xinyunlian.jinfu.user.dto.req.PasswordDto;
import com.xinyunlian.jinfu.user.enums.EOperationType;
import com.xinyunlian.jinfu.user.enums.ESmsSendType;
import com.xinyunlian.jinfu.user.enums.ESource;
import com.xinyunlian.jinfu.user.service.OperationLogService;
import com.xinyunlian.jinfu.user.service.SmsService;
import com.xinyunlian.jinfu.user.service.UserService;
import com.xinyunlian.jinfu.yunma.dto.YMMemberDto;
import com.xinyunlian.jinfu.yunma.dto.YmMemberBizDto;
import com.xinyunlian.jinfu.yunma.dto.YmThirdConfigDto;
import com.xinyunlian.jinfu.yunma.dto.YmThirdUserDto;
import com.xinyunlian.jinfu.yunma.enums.EMemberAuditStatus;
import com.xinyunlian.jinfu.yunma.enums.EMemberIntoStatus;
import com.xinyunlian.jinfu.yunma.enums.EMemberStatus;
import com.xinyunlian.jinfu.yunma.service.YMMemberService;
import com.xinyunlian.jinfu.yunma.service.YmThirdConfigService;
import com.xinyunlian.jinfu.yunma.service.YmThirdUserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by menglei on 2016年12月13日.
 */
@RestController
@RequestMapping(value = "open-api")
public class ApiOpenController {

    @Autowired
    private StoreService storeService;
    @Autowired
    private UserService userService;
    @Autowired
    private YMMemberService yMMemberService;
    @Autowired
    private YmTradeService ymTradeService;
    @Autowired
    private YmThirdConfigService ymThirdConfigService;
    @Autowired
    private BankService bankService;
    @Autowired
    private CloudCodeService cloudCodeService;
    @Autowired
    private SmsService smsService;
    @Autowired
    private OperationLogService logService;
    @Autowired
    private YmThirdUserService ymThirdUserService;
    @Autowired
    private CorpBankService corpBankService;

    /**
     * 验签 mobile=13967899785&outId=11&key=gEV9GhV413vDlDtc md5 32位大写
     *
     * @param checkSignOpenApiDto
     * @return
     */
    @OpenApi
    @RequestMapping(value = "/checkSign", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "验签")
    public ResultDto<Object> checkSign(@RequestBody CheckSignOpenApiDto checkSignOpenApiDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }
        if (StringUtils.isEmpty(checkSignOpenApiDto.getOutId())) {
            return ResultDtoFactory.toNack("outId必传");
        }
        if (checkSignOpenApiDto.getMobile().length() != 11) {
            return ResultDtoFactory.toNack("手机号格式不正确");
        }
        return ResultDtoFactory.toAck("验签成功");
    }

    /**
     * 获取短信验证码
     *
     * @param checkSignOpenApiDto
     * @return
     */
    @OpenApi
    @RequestMapping(value = "/mobile/getCode", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> getVerifyCode(@RequestBody CheckSignOpenApiDto checkSignOpenApiDto) {
        if (checkSignOpenApiDto.getMobile().length() != 11) {
            return ResultDtoFactory.toNack("手机号格式不正确");
        }
        if (StringUtils.isEmpty(checkSignOpenApiDto.getOutId())) {
            return ResultDtoFactory.toNack("outId必传");
        }
        String verifyCode = smsService.getVerifyCode(checkSignOpenApiDto.getMobile(), ESmsSendType.LOGIN_PWD);
        System.out.printf(verifyCode);
        return ResultDtoFactory.toAck("短信发送成功");
    }

    /**
     * 用户注册(合作方内嵌页)
     *
     * @param registerOpenApiDto
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> register(@RequestBody RegisterOpenApiDto registerOpenApiDto, BindingResult result) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (result.hasErrors()) {
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }
        if (StringUtils.isEmpty(registerOpenApiDto.getOutId())) {
            return ResultDtoFactory.toNack("outId必传");
        }
        //验签
        YmThirdConfigDto ymThirdConfigDto = ymThirdConfigService.findByAppId(registerOpenApiDto.getAppId());
        if (ymThirdConfigDto == null) {
            return ResultDtoFactory.toNack("签名认证失败");
        }
        if (registerOpenApiDto.getMobile().length() != 11) {
            return ResultDtoFactory.toNack("手机号格式不正确");
        }
        SortedMap<String, String> sortedMap = new TreeMap<>();
        sortedMap.put("mobile", registerOpenApiDto.getMobile());
        sortedMap.put("outId", registerOpenApiDto.getOutId());
        if (!SignUtil.createSign(sortedMap, ymThirdConfigDto.getKey()).equals(registerOpenApiDto.getSign())) {
            return ResultDtoFactory.toNack("签名认证失败");
        }
        //验证验证码
        boolean flag = smsService.confirmVerifyCode(registerOpenApiDto.getMobile(), registerOpenApiDto.getVerifyCode(), ESmsSendType.LOGIN_PWD);
        if (!flag) {
            return ResultDtoFactory.toNack("验证码错误");
        }
        UserInfoDto userInfoDto = userService.findUserByMobile(registerOpenApiDto.getMobile());
        if (userInfoDto == null) {
            //创建用户信息
            userInfoDto = new UserInfoDto();
            userInfoDto.setMobile(registerOpenApiDto.getMobile());
            userInfoDto.setLoginPassword(UUID.randomUUID().toString());
            userInfoDto.setSource(ESource.THIRD_PARTY);
            userInfoDto = userService.saveUser(userInfoDto);
            //记录注册日志
            PasswordDto passwordDto = new PasswordDto();
            passwordDto.setUserId(userInfoDto.getUserId());
            logService.saveLog(passwordDto, EOperationType.REGISTER);
        }
        YmThirdUserDto ymThirdUserDto = ymThirdUserService.findByUserId(userInfoDto.getUserId());
        if (ymThirdUserDto != null) {
            if (ymThirdUserDto.getMemberId() == null) {
                return ResultDtoFactory.toNack("开通申请中,请在云联云码公众号中完成相应操作");
            } else {
                return ResultDtoFactory.toNack("已绑定开通了云联云码支付");
            }

        }
        ymThirdUserDto = new YmThirdUserDto();
        ymThirdUserDto.setThirdConfigId(ymThirdConfigDto.getId());
        ymThirdUserDto.setOutId(registerOpenApiDto.getOutId());
        ymThirdUserDto.setMobile(userInfoDto.getMobile());
        ymThirdUserDto.setUserId(userInfoDto.getUserId());
        ymThirdUserService.save(ymThirdUserDto);
        //清除验证码
        smsService.clearVerifyCode(registerOpenApiDto.getMobile(), ESmsSendType.LOGIN_PWD);
        return ResultDtoFactory.toAck("注册成功");
    }

    /**
     * 获取支付流水
     *
     * @param tradeOpenApiDto
     * @return
     */
    @OpenApi
    @ResponseBody
    @RequestMapping(value = "/trade", method = RequestMethod.POST)
    public ResultDto<ThirdTradeDto> trade(@RequestBody TradeOpenApiDto tradeOpenApiDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }
        YmThirdConfigDto ymThirdConfigDto = ymThirdConfigService.findByAppId(tradeOpenApiDto.getAppId());
        if (ymThirdConfigDto == null) {
            return ResultDtoFactory.toNack("签名认证失败");
        }
        YmTradeDto ymTradeDto = ymTradeService.findByTradeNo(tradeOpenApiDto.getTradeNo());
        if (ymTradeDto == null) {
            return ResultDtoFactory.toNack("流水不存在");
        }
        YMMemberDto yMMemberDto = yMMemberService.getMemberByMemberNo(ymTradeDto.getMemberNo());
        if (yMMemberDto == null) {
            return ResultDtoFactory.toNack("云码店铺不存在");
        }
        YmThirdUserDto ymThirdUserDto = ymThirdUserService.findByMemberIdAndThirdConfigId(yMMemberDto.getId(), ymThirdConfigDto.getId());
        if (ymThirdUserDto == null || ymThirdUserDto.getMemberId() == null) {
            return ResultDtoFactory.toNack("云码关系不存在");
        }
        ThirdTradeDto thirdTradeDto = new ThirdTradeDto();
        thirdTradeDto.setTradeNo(ymTradeDto.getTradeNo());
        thirdTradeDto.setAmount(String.valueOf(ymTradeDto.getTransAmt()));
        thirdTradeDto.setType(ymTradeDto.getType().getCode());
        thirdTradeDto.setStatus(ymTradeDto.getStatus().getCode());
        thirdTradeDto.setCreates(DateHelper.formatDate(ymTradeDto.getCreateTs(), "yyyy-MM-dd HH:mm:ss"));
        thirdTradeDto.setMobile(ymThirdUserDto.getMobile());
        thirdTradeDto.setOutId(ymThirdUserDto.getOutId());
        thirdTradeDto.setQrCodeNo(yMMemberDto.getQrcodeNo());
        thirdTradeDto.setQrCodeUrl(yMMemberDto.getQrcodeUrl());
        thirdTradeDto.setBizCode(ymTradeDto.getBizCode().getCode());
        return ResultDtoFactory.toAck("获取成功", thirdTradeDto);
    }

    /**
     * 商家扫码支付
     *
     * @param merchantScanOpenApiDto
     * @return
     */
    @OpenApi
    @ResponseBody
    @RequestMapping(value = "/merchantscan", method = RequestMethod.POST)
    public ResultDto<Map<String, String>> merchantscan(@RequestBody MerchantScanOpenApiDto merchantScanOpenApiDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }
        if (merchantScanOpenApiDto.getAmount().compareTo(BigDecimal.ZERO) == -1 || merchantScanOpenApiDto.getAmount().compareTo(BigDecimal.ZERO) == 0) {
            return ResultDtoFactory.toNack("支付金额必须大于0");
        }
        YMMemberDto yMMemberDto = yMMemberService.getMemberByQrCodeNo(merchantScanOpenApiDto.getQrCodeNo());
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
        if (yMMemberDto.getBankCardId() != null) {
            BankCardDto bankCardDto = bankService.getBankCard(yMMemberDto.getBankCardId());//银行卡
            if (bankCardDto == null) {
                return ResultDtoFactory.toNack("未添加银行卡");
            }
            bankCardNo = bankCardDto.getBankCardNo();
        }
        if (yMMemberDto.getCorpBankId() != null) {
            CorpBankDto corpBankDto = corpBankService.getCorpBankById(yMMemberDto.getCorpBankId());//对公账号
            if (corpBankDto == null) {
                return ResultDtoFactory.toNack("未添加银行卡");
            }
            bankCardNo = corpBankDto.getAccount();
        }
        //支付
        //String orderNo = "8" + String.valueOf(System.currentTimeMillis()).substring(0, 10) + RandomUtil.getNumberStr(3);//生成订单号
        String orderNo = ymTradeService.getOrderNo();

        CloudCodePayOrderDto cloudCodePayOrderDto = new CloudCodePayOrderDto();
        cloudCodePayOrderDto.setPayQrCode(merchantScanOpenApiDto.getPayCode());
        cloudCodePayOrderDto.setPartnerId(AppConfigUtil.getConfig("cashier.partnerid"));
        cloudCodePayOrderDto.setMerchantId(yMMemberDto.getMemberNo());
        cloudCodePayOrderDto.setWalletType(CashierConsts.WalletType.WALLET_TYPE_WECHATPAY);//1alipay 2wechat
        cloudCodePayOrderDto.setSrcAmt(merchantScanOpenApiDto.getAmount());//0.01
        cloudCodePayOrderDto.setDiscountAmt(BigDecimal.valueOf(0));
        cloudCodePayOrderDto.setGoodsDesc(storeInfDto.getStoreName());//storeName
        //cloudCodePayOrderDto.setDeviceId();
        cloudCodePayOrderDto.setNotifyUrl(AppConfigUtil.getConfig("cashier.notify.url"));//异步回调
        cloudCodePayOrderDto.setOutTradeNo(orderNo);
        cloudCodePayOrderDto.setUserId(StringUtils.EMPTY);
        cloudCodePayOrderDto.setAppId(AppConfigUtil.getConfig("cashier.appid"));
        MctScanPayResp mctScanPayResp = new MctScanPayResp();
        try {
            mctScanPayResp = cloudCodeService.merchantScanPay(cloudCodePayOrderDto);
        } catch (Exception e) {
            return ResultDtoFactory.toNack("支付失败");
        }

        //生成订单
        YmTradeDto ymTradeDto = new YmTradeDto();
        ymTradeDto.setTradeNo(orderNo);
        ymTradeDto.setOutTradeNo(mctScanPayResp.getOrderNo());
        ymTradeDto.setMemberNo(yMMemberDto.getMemberNo());
        EBizCode bizCode = EBizCode.WECHAT;
        YmMemberBizDto memberBizDto = wechatBizDto;
        if (CashierConsts.WalletType.WALLET_TYPE_ALIPAY.equals(mctScanPayResp.getWalletType())) {
            bizCode = EBizCode.ALLIPAY;
            memberBizDto = alipayBizDto;
        }
        ymTradeDto.setBizCode(bizCode);
        ymTradeDto.setTransAmt(merchantScanOpenApiDto.getAmount());
        ymTradeDto.setTransFee(merchantScanOpenApiDto.getAmount().multiply(memberBizDto.getRate()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
        ymTradeDto.setCardNo(bankCardNo);
        ymTradeDto.setStatus(ETradeStatus.UNPAID);
        ymTradeDto.setType(ETradeType.MERCHANT_SCAN);
        ymTradeDto = ymTradeService.saveTrade(ymTradeDto);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("tradeNo", ymTradeDto.getTradeNo());
        resultMap.put("amount", String.valueOf(ymTradeDto.getTransAmt()));
        resultMap.put("type", ymTradeDto.getType().getCode());
        resultMap.put("status", ymTradeDto.getStatus().getCode());
        resultMap.put("creates", DateHelper.formatDate(ymTradeDto.getCreateTs(), "yyyy-MM-dd HH:mm:ss"));
        resultMap.put("bizCode", ymTradeDto.getBizCode().getCode());
        return ResultDtoFactory.toAck("下单成功", resultMap);
    }

    /**
     * 用户扫码支付
     *
     * @param userScanOpenApiDto
     * @return
     */
    @OpenApi
    @ResponseBody
    @RequestMapping(value = "/userscan", method = RequestMethod.POST)
    public ResultDto<Map<String, String>> userscan(@RequestBody UserScanOpenApiDto userScanOpenApiDto, BindingResult result) {
        if (result.hasErrors()) {
            return ResultDtoFactory.toNack(result.getFieldError().getDefaultMessage());
        }
        if (!CashierConsts.WalletType.WALLET_TYPE_WECHATPAY.equals(userScanOpenApiDto.getType())
                && !CashierConsts.WalletType.WALLET_TYPE_ALIPAY.equals(userScanOpenApiDto.getType())) {
            return ResultDtoFactory.toNack("支付类型只支持支付宝,微信");
        }
        if (userScanOpenApiDto.getAmount().compareTo(BigDecimal.ZERO) == -1 || userScanOpenApiDto.getAmount().compareTo(BigDecimal.ZERO) == 0) {
            return ResultDtoFactory.toNack("支付金额必须大于0");
        }
        YMMemberDto yMMemberDto = yMMemberService.getMemberByQrCodeNo(userScanOpenApiDto.getQrCodeNo());
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
        if (yMMemberDto.getBankCardId() != null) {
            BankCardDto bankCardDto = bankService.getBankCard(yMMemberDto.getBankCardId());//银行卡
            if (bankCardDto == null) {
                return ResultDtoFactory.toNack("未添加银行卡");
            }
            bankCardNo = bankCardDto.getBankCardNo();
        }
        if (yMMemberDto.getCorpBankId() != null) {
            CorpBankDto corpBankDto = corpBankService.getCorpBankById(yMMemberDto.getCorpBankId());//对公账号
            if (corpBankDto == null) {
                return ResultDtoFactory.toNack("未添加银行卡");
            }
            bankCardNo = corpBankDto.getAccount();
        }
        //支付
        //String orderNo = "8" + String.valueOf(System.currentTimeMillis()).substring(0, 10) + RandomUtil.getNumberStr(3);//生成订单号
        String orderNo = ymTradeService.getOrderNo();

        CloudCodePayOrderDto cloudCodePayOrderDto = new CloudCodePayOrderDto();
        cloudCodePayOrderDto.setPartnerId(AppConfigUtil.getConfig("cashier.partnerid"));
        cloudCodePayOrderDto.setMerchantId(yMMemberDto.getMemberNo());
        cloudCodePayOrderDto.setWalletType(userScanOpenApiDto.getType());//1alipay 2wechat
        cloudCodePayOrderDto.setSrcAmt(userScanOpenApiDto.getAmount());//0.01
        cloudCodePayOrderDto.setDiscountAmt(BigDecimal.valueOf(0));
        cloudCodePayOrderDto.setGoodsDesc(storeInfDto.getStoreName());//storeName
        //cloudCodePayOrderDto.setDeviceId();
        cloudCodePayOrderDto.setNotifyUrl(AppConfigUtil.getConfig("cashier.notify.url"));//异步回调
        cloudCodePayOrderDto.setOutTradeNo(orderNo);
        cloudCodePayOrderDto.setUserId(StringUtils.EMPTY);
        cloudCodePayOrderDto.setAppId(AppConfigUtil.getConfig("cashier.appid"));
        CloudCodePrepayOrderDto cloudCodePrepayOrderDto = new CloudCodePrepayOrderDto();
        try {
            cloudCodePrepayOrderDto = cloudCodeService.smartpayCSBPay(cloudCodePayOrderDto);
        } catch (Exception e) {
            return ResultDtoFactory.toNack("下单失败,请联系客服");
        }

        //生成订单
        YmTradeDto ymTradeDto = new YmTradeDto();
        ymTradeDto.setTradeNo(orderNo);
        ymTradeDto.setOutTradeNo(cloudCodePrepayOrderDto.getOrderNo());
        ymTradeDto.setMemberNo(yMMemberDto.getMemberNo());
        EBizCode bizCode = EBizCode.WECHAT;
        YmMemberBizDto memberBizDto = wechatBizDto;
        if (CashierConsts.WalletType.WALLET_TYPE_ALIPAY.equals(userScanOpenApiDto.getType())) {
            bizCode = EBizCode.ALLIPAY;
            memberBizDto = alipayBizDto;
        }
        ymTradeDto.setBizCode(bizCode);
        ymTradeDto.setTransAmt(userScanOpenApiDto.getAmount());
        ymTradeDto.setTransFee(userScanOpenApiDto.getAmount().multiply(memberBizDto.getRate()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
        ymTradeDto.setCardNo(bankCardNo);
        ymTradeDto.setStatus(ETradeStatus.UNPAID);
        ymTradeDto.setType(ETradeType.USER_SCAN);
        ymTradeDto = ymTradeService.saveTrade(ymTradeDto);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("tradeNo", ymTradeDto.getTradeNo());
        resultMap.put("amount", String.valueOf(ymTradeDto.getTransAmt()));
        resultMap.put("type", ymTradeDto.getType().getCode());
        resultMap.put("status", ymTradeDto.getStatus().getCode());
        resultMap.put("creates", DateHelper.formatDate(ymTradeDto.getCreateTs(), "yyyy-MM-dd HH:mm:ss"));
        resultMap.put("bizCode", ymTradeDto.getBizCode().getCode());
        resultMap.put("payUrl", cloudCodePrepayOrderDto.getSdkParam());
        return ResultDtoFactory.toAck("下单成功", resultMap);
    }

}
