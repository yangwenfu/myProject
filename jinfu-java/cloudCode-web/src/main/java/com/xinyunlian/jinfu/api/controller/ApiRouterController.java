package com.xinyunlian.jinfu.api.controller;

import com.xinyunlian.jinfu.api.dto.RouterOpenApiDto;
import com.xinyunlian.jinfu.api.service.ApiThirdService;
import com.xinyunlian.jinfu.api.validate.util.SignUtil;
import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;
import com.xinyunlian.jinfu.area.service.SysAreaInfService;
import com.xinyunlian.jinfu.bank.dto.BankCardDto;
import com.xinyunlian.jinfu.bank.dto.CorpBankDto;
import com.xinyunlian.jinfu.bank.service.BankService;
import com.xinyunlian.jinfu.bank.service.CorpBankService;
import com.xinyunlian.jinfu.cashier.consts.CashierConsts;
import com.xinyunlian.jinfu.cashier.dto.CloudCodePayOrderDto;
import com.xinyunlian.jinfu.cashier.dto.CloudCodePrepayOrderDto;
import com.xinyunlian.jinfu.cashier.dto.MerchantSupportedChannelDetailDto;
import com.xinyunlian.jinfu.cashier.service.CloudCodeService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.util.*;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.trade.dto.YmTradeDto;
import com.xinyunlian.jinfu.trade.enums.EBizCode;
import com.xinyunlian.jinfu.trade.enums.ETradeStatus;
import com.xinyunlian.jinfu.trade.enums.ETradeType;
import com.xinyunlian.jinfu.trade.service.YmTradeService;
import com.xinyunlian.jinfu.yunma.dto.*;
import com.xinyunlian.jinfu.yunma.enums.*;
import com.xinyunlian.jinfu.yunma.service.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by menglei on 2017/1/5.
 */
@Controller
@RequestMapping(value = "open-api/router")
public class ApiRouterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiRouterController.class);

    @Autowired
    private YMRouterAgentService yMRouterAgentService;
    @Autowired
    private YMMemberService yMMemberService;
    @Autowired
    private YmTradeService ymTradeService;
    @Autowired
    private BankService bankService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private YMProdService yMProdService;
    @Autowired
    private SysAreaInfService sysAreaInfService;
    @Autowired
    private CloudCodeService cloudCodeService;
    @Autowired
    private CorpBankService corpBankService;
    @Autowired
    private YmThirdUserService ymThirdUserService;
    @Autowired
    private ApiThirdService apiThirdService;
    @Autowired
    private YmThirdConfigService ymThirdConfigService;
    @Autowired
    private YmPayChannelService ymPayChannelService;

    /**
     * 获取路由跳转地址
     *
     * @param routerOpenApiDto
     * @return
     */
    @RequestMapping(value = "/getUrl", method = RequestMethod.POST)
    @ResponseBody
    public ResultDto<Object> getUrl(HttpServletRequest request, @RequestBody RouterOpenApiDto routerOpenApiDto) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        routerOpenApiDto.setUserAgent(request.getHeader("User-Agent"));//直接获取ua

        YMRouterAgentDto dto = yMRouterAgentService.findLocalByUserAgent(routerOpenApiDto.getUserAgent().toLowerCase());
        if (dto == null) {
            return ResultDtoFactory.toNack("路由不存在");
        }
        YMMemberDto yMMemberDto = yMMemberService.getMemberByQrCodeNo(routerOpenApiDto.getQrCodeNo());
        if (yMMemberDto == null) {//云码不存在
            return ResultDtoFactory.toAck("获取成功", null);
        }
        if (StringUtils.equals(yMMemberDto.getMemberStatus().getCode(), EMemberStatus.DISABLE.getCode())) {
            return ResultDtoFactory.toNack("云码已停用");
        }
        EBizCode bizCode;
        if (StringUtils.equals(dto.getUserAgent(), EUserAgent.WECHATPAY.getCode())) {//微信支付
            bizCode = EBizCode.WECHAT;
        } else if (StringUtils.equals(dto.getUserAgent(), EUserAgent.ALIPAY.getCode())) {//支付宝支付
            bizCode = EBizCode.ALLIPAY;
        }  else if (StringUtils.equals(dto.getUserAgent(), EUserAgent.BESTPAY.getCode())) {//翼支付
            bizCode = EBizCode.BESTPAY;
        } else if (StringUtils.equals(dto.getUserAgent(), EUserAgent.JDPAY.getCode())) {//京东支付
            bizCode = EBizCode.JDPAY;
        }  else if (StringUtils.equals(dto.getUserAgent(), EUserAgent.QQPAY.getCode())) {//QQ支付
            bizCode = EBizCode.QQPAY;
        }  else if (StringUtils.equals(dto.getUserAgent(), EUserAgent.BAIDUPAY.getCode())) {//百度支付
            bizCode = EBizCode.BAIDUPAY;
        } else if (StringUtils.equals(dto.getUserAgent(), EUserAgent.DEALER.getCode())) {//小伙伴
            String routerUrl = "?id=" + yMMemberDto.getStoreId() + routerUrl(routerOpenApiDto.getUserAgent());
            return ResultDtoFactory.toAck("获取成功", dto.getUrl() + routerUrl);
        } else {
            //其它情况直接跳转
            return ResultDtoFactory.toAck("获取成功", dto.getUrl());
        }
        StoreInfDto storeInfDto = storeService.findByStoreId(yMMemberDto.getStoreId());
        if (storeInfDto == null) {
            return ResultDtoFactory.toNack("店铺不存在");
        }
        //判断云码店铺进件状态
        if (EMemberIntoStatus.INTO_FAILED.equals(yMMemberDto.getMemberIntoStatus())) {
            return ResultDtoFactory.toNack(EMemberIntoStatus.INTO_FAILED.getCode());
        } else if (EMemberIntoStatus.INTO_ING.equals(yMMemberDto.getMemberIntoStatus())) {
            return ResultDtoFactory.toNack(EMemberIntoStatus.INTO_ING.getCode());
        }
        //判断云码店铺审核状态
        if (EMemberAuditStatus.FAILED.equals(yMMemberDto.getMemberAuditStatus())) {
            return ResultDtoFactory.toNack(EMemberAuditStatus.FAILED.getCode());
        } else if (EMemberAuditStatus.UNAUDIT.equals(yMMemberDto.getMemberAuditStatus())) {
            return ResultDtoFactory.toNack(EMemberAuditStatus.UNAUDIT.getCode());
        } else if (EMemberAuditStatus.AUDITING.equals(yMMemberDto.getMemberAuditStatus())) {
            return ResultDtoFactory.toNack(EMemberAuditStatus.AUDITING.getCode());
        }
        if (StringUtils.isEmpty(routerOpenApiDto.getAmount())) {
            //跳转到支付页，输金额
            String url = dto.getUrl() + "?codeNo=" + routerOpenApiDto.getQrCodeNo() + "&memberName=" + storeInfDto.getStoreName();
            if (StringUtils.isNotEmpty(routerOpenApiDto.getCost())) {
                url = url + "&cost=" + routerOpenApiDto.getCost();
            }
            //跳转到支付页，输金额
            return ResultDtoFactory.toAck("获取成功", url);
        }
        YmMemberBizDto ymMemberBizDto = yMMemberService.getMemberBizByMemberNoAndBizCode(yMMemberDto.getMemberNo(), bizCode);//费率
        if (ymMemberBizDto == null) {
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
        //判断店铺所在的地区是否是业务配置的地区范围内
        SysAreaInfDto sysAreaInfDto = sysAreaInfService.getSysAreaInfById(Long.valueOf(storeInfDto.getDistrictId()));
        Boolean flag = yMProdService.checkProdArea(EYmProd.YM0001.getCode(), Long.valueOf(storeInfDto.getDistrictId()), sysAreaInfDto.getTreePath());
        if (!flag) {
            return ResultDtoFactory.toNack("该地区尚未开通此业务");
        }
        //判断云码是否激活
        if (!yMMemberDto.getActivate()) {
            return ResultDtoFactory.toNack("云码未激活");
        }
        //支付跳转
        //String orderNo = "8" + String.valueOf(System.currentTimeMillis()).substring(0, 10) + RandomUtil.getNumberStr(3);//生成订单号
        String orderNo = ymTradeService.getOrderNo();
        String walletType = null;
        if (bizCode.equals(EBizCode.ALLIPAY)) {
            walletType = CashierConsts.WalletType.WALLET_TYPE_ALIPAY;
        } else if (bizCode.equals(EBizCode.WECHAT)) {
            walletType = CashierConsts.WalletType.WALLET_TYPE_WECHATPAY;
        } else if (bizCode.equals(EBizCode.BESTPAY)) {
            walletType = CashierConsts.WalletType.WALLET_TYPE_BESTPAY;
        } else if (bizCode.equals(EBizCode.JDPAY)) {
            walletType = CashierConsts.WalletType.WALLET_TYPE_JDPAY;
        } else if (bizCode.equals(EBizCode.QQPAY)) {
            walletType = CashierConsts.WalletType.WALLET_TYPE_QQPAY;
        } else if (bizCode.equals(EBizCode.BAIDUPAY)) {
            walletType = CashierConsts.WalletType.WALLET_TYPE_BAIDUPAY;
        }
        CloudCodePayOrderDto cloudCodePayOrderDto = new CloudCodePayOrderDto();
        cloudCodePayOrderDto.setPartnerId(AppConfigUtil.getConfig("cashier.partnerid"));
        cloudCodePayOrderDto.setMerchantId(yMMemberDto.getMemberNo());
        cloudCodePayOrderDto.setWalletType(walletType);//1alipay 2wechat 3bestpay 4jdpay
        cloudCodePayOrderDto.setSrcAmt(new BigDecimal(routerOpenApiDto.getAmount()));//0.01
        cloudCodePayOrderDto.setGoodsDesc(storeInfDto.getStoreName());//storeName
        //cloudCodePayOrderDto.setDeviceId();
        cloudCodePayOrderDto.setOutTradeNo(orderNo);
        cloudCodePayOrderDto.setNotifyUrl(AppConfigUtil.getConfig("cashier.notify.url"));//异步回调
        cloudCodePayOrderDto.setUserId(routerOpenApiDto.getUserId());
        cloudCodePayOrderDto.setDiscountAmt(BigDecimal.valueOf(0));//优惠金额
        cloudCodePayOrderDto.setReturnUrl(AppConfigUtil.getConfig("cashier.return.url"));//同步回调
        cloudCodePayOrderDto.setAppId(AppConfigUtil.getConfig("cashier.appid"));
        cloudCodePayOrderDto.setUserIp("0.0.0.0");
        CloudCodePrepayOrderDto cloudCodePrepayOrderDto = null;

        try {
            //支付通道选择
            List<MerchantSupportedChannelDetailDto> merchantSupportedChannelDtos = cloudCodeService.findMerchantChannelDetails(yMMemberDto.getMemberNo());
            LOGGER.info("支持通道信息===================：" + JsonUtil.toJson(merchantSupportedChannelDtos));
            MerchantSupportedChannelDetailDto merchantSupportedChannelDetailDto = new MerchantSupportedChannelDetailDto();
            if (CollectionUtils.isEmpty(merchantSupportedChannelDtos)) {
                return ResultDtoFactory.toNack("该云码暂时无法使用，请联系客服");
            } else if (merchantSupportedChannelDtos.size() == 1) {//返回一个通道
                merchantSupportedChannelDetailDto = merchantSupportedChannelDtos.get(0);
            } else {//返回多个通道
                List<String> channelList = new ArrayList<>();
                Map<String, MerchantSupportedChannelDetailDto> map = new HashMap<>();
                for (MerchantSupportedChannelDetailDto detailDto : merchantSupportedChannelDtos) {
                    channelList.add(detailDto.getChannelProvider());
                    map.put(detailDto.getChannelProvider(), detailDto);
                }
                //特殊处理
                if (bizCode.equals(EBizCode.BESTPAY) && map.get(EYmPayChannel.PINGAN.getCode()) != null && map.get(EYmPayChannel.PINGAN.getCode()).getBestpay()) {//平安唯一
                    LOGGER.info("平安翼支付====：" + JsonUtil.toJson(cloudCodePayOrderDto));
                    cloudCodePrepayOrderDto = cloudCodeService.pinganBestpay(cloudCodePayOrderDto);
                } else if (bizCode.equals(EBizCode.BAIDUPAY) && map.get(EYmPayChannel.SMARTPAY.getCode()) != null && map.get(EYmPayChannel.PINGAN.getCode()).getBaidupay()) {//支付通唯一
                    LOGGER.info("支持通百度支付====：" + JsonUtil.toJson(cloudCodePayOrderDto));
                    cloudCodePrepayOrderDto = cloudCodeService.smartpayCSBPay(cloudCodePayOrderDto);
                } else if (bizCode.equals(EBizCode.QQPAY) && map.get(EYmPayChannel.SMARTPAY.getCode()) != null && map.get(EYmPayChannel.PINGAN.getCode()).getQqpay()) {//支付通唯一
                    LOGGER.info("支持通QQ支付====：" + JsonUtil.toJson(cloudCodePayOrderDto));
                    cloudCodePrepayOrderDto = cloudCodeService.smartpayCSBPay(cloudCodePayOrderDto);
                } else {
                    List<YmPayChannelDto> list = ymPayChannelService.findByChannel(Long.valueOf(storeInfDto.getCityId()), sysAreaInfDto.getTreePath(), channelList);
                    if (CollectionUtils.isEmpty(list)) {
                        merchantSupportedChannelDetailDto = merchantSupportedChannelDtos.get(0);
                    } else {
                        merchantSupportedChannelDetailDto = map.get(list.get(0).getChannel().getCode());
                        if (merchantSupportedChannelDetailDto == null) {//通道路由查询无结果
                            merchantSupportedChannelDetailDto = merchantSupportedChannelDtos.get(0);
                        }
                    }
                }
            }
            if (cloudCodePrepayOrderDto == null) {
                if (EYmPayChannel.PINGAN.getCode().equals(merchantSupportedChannelDetailDto.getChannelProvider())) {
                    if (bizCode.equals(EBizCode.ALLIPAY) && merchantSupportedChannelDetailDto.getAlipay()) {
                        LOGGER.info("平安支付宝====：" + JsonUtil.toJson(cloudCodePayOrderDto));
                        cloudCodePrepayOrderDto = cloudCodeService.pinganAlipay(cloudCodePayOrderDto);
                    } else if (bizCode.equals(EBizCode.WECHAT) && merchantSupportedChannelDetailDto.getWechat()) {
                        LOGGER.info("平安微信支付====：" + JsonUtil.toJson(cloudCodePayOrderDto));
                        cloudCodePrepayOrderDto = cloudCodeService.pinganWechatpay(cloudCodePayOrderDto);
                    } else if (bizCode.equals(EBizCode.BESTPAY) && merchantSupportedChannelDetailDto.getBestpay()) {
                        LOGGER.info("平安翼支付====：" + JsonUtil.toJson(cloudCodePayOrderDto));
                        cloudCodePrepayOrderDto = cloudCodeService.pinganBestpay(cloudCodePayOrderDto);
                    } else if (bizCode.equals(EBizCode.JDPAY) && merchantSupportedChannelDetailDto.getJdpay()) {
                        LOGGER.info("平安京东支付====：" + JsonUtil.toJson(cloudCodePayOrderDto));
                        cloudCodePrepayOrderDto = cloudCodeService.pinganJdpay(cloudCodePayOrderDto);
                    } else {
                        return ResultDtoFactory.toNack("当前支付方式暂不支持");
                    }
                }  else if (EYmPayChannel.SMARTPAY.getCode().equals(merchantSupportedChannelDetailDto.getChannelProvider())) {
                    if (bizCode.equals(EBizCode.ALLIPAY) && merchantSupportedChannelDetailDto.getAlipay()) {
                        LOGGER.info("支付通支付宝====：" + JsonUtil.toJson(cloudCodePayOrderDto));
                        cloudCodePrepayOrderDto = cloudCodeService.smartpayJsSale(cloudCodePayOrderDto);
                    } else if (bizCode.equals(EBizCode.WECHAT) && merchantSupportedChannelDetailDto.getWechat()) {
                        LOGGER.info("支付通微信支付====：" + JsonUtil.toJson(cloudCodePayOrderDto));
                        cloudCodePayOrderDto.setWechatAppId(AppConfigUtil.getConfig("wechat.appid"));
                        cloudCodePrepayOrderDto = cloudCodeService.smartpayJsSale(cloudCodePayOrderDto);
                    } else if (bizCode.equals(EBizCode.BAIDUPAY) && merchantSupportedChannelDetailDto.getBaidupay()) {
                        LOGGER.info("支付通百度支付====：" + JsonUtil.toJson(cloudCodePayOrderDto));
                        cloudCodePrepayOrderDto = cloudCodeService.smartpayCSBPay(cloudCodePayOrderDto);
                    } else if (bizCode.equals(EBizCode.JDPAY) && merchantSupportedChannelDetailDto.getJdpay()) {
                        LOGGER.info("支付通京东支付====：" + JsonUtil.toJson(cloudCodePayOrderDto));
                        cloudCodePrepayOrderDto = cloudCodeService.smartpayCSBPay(cloudCodePayOrderDto);
                    } else if (bizCode.equals(EBizCode.QQPAY) && merchantSupportedChannelDetailDto.getQqpay()) {
                        LOGGER.info("支付通QQ支付====：" + JsonUtil.toJson(cloudCodePayOrderDto));
                        cloudCodePrepayOrderDto = cloudCodeService.smartpayCSBPay(cloudCodePayOrderDto);
                    } else {
                        return ResultDtoFactory.toNack("当前支付方式暂不支持");
                    }
                }  else if (EYmPayChannel.ZHONGMA.getCode().equals(merchantSupportedChannelDetailDto.getChannelProvider())) {
                    if (bizCode.equals(EBizCode.ALLIPAY) && merchantSupportedChannelDetailDto.getAlipay()) {
                        LOGGER.info("众码支付宝====：" + JsonUtil.toJson(cloudCodePayOrderDto));
                        cloudCodePrepayOrderDto = cloudCodeService.pay(cloudCodePayOrderDto);
                    } else if (bizCode.equals(EBizCode.WECHAT) && merchantSupportedChannelDetailDto.getWechat()) {
                        LOGGER.info("众码微信支付====：" + JsonUtil.toJson(cloudCodePayOrderDto));
                        cloudCodePrepayOrderDto = cloudCodeService.pay(cloudCodePayOrderDto);
                    } else {
                        return ResultDtoFactory.toNack("当前支付方式暂不支持");
                    }
                } else {
                    return ResultDtoFactory.toNack("该云码暂时无法使用，请联系客服");
                }
            }
            if (cloudCodePrepayOrderDto == null) {
                return ResultDtoFactory.toNack("网络问题，请稍后再试");
            }
        } catch (Exception e) {
            LOGGER.error("支付异常{}", JsonUtil.toJson(cloudCodePayOrderDto), e);
            return ResultDtoFactory.toNack("支付异常，请联系客服");
        }
        //生成订单
        YmTradeDto ymTradeDto = new YmTradeDto();
        ymTradeDto.setTradeNo(orderNo);
        ymTradeDto.setOutTradeNo(cloudCodePrepayOrderDto.getOrderNo());
        ymTradeDto.setMemberNo(yMMemberDto.getMemberNo());
        ymTradeDto.setBizCode(bizCode);
        ymTradeDto.setTransAmt(new BigDecimal(routerOpenApiDto.getAmount()));
        ymTradeDto.setTransFee(new BigDecimal(routerOpenApiDto.getAmount()).multiply(ymMemberBizDto.getRate()).divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP));
        ymTradeDto.setCardNo(bankCardNo);
        ymTradeDto.setStatus(ETradeStatus.UNPAID);
        ymTradeDto.setType(ETradeType.USER_SCAN);
        ymTradeDto.setOpenid(routerOpenApiDto.getUserId());
        ymTradeDto = ymTradeService.saveTrade(ymTradeDto);
        //推送通知给合作方
        YmThirdUserDto ymThirdUserDto = ymThirdUserService.findByMemberId(yMMemberDto.getId());
        if (ymThirdUserDto != null) {
            YmThirdConfigDto ymThirdConfigDto = ymThirdConfigService.findById(ymThirdUserDto.getThirdConfigId());
            if (ymThirdConfigDto == null) {
                return ResultDtoFactory.toNack("appId不存在");
            }
            SortedMap<String, String> sortedMap = new TreeMap<>();
            sortedMap.put("mobile", ymThirdUserDto.getMobile());
            sortedMap.put("outId", ymThirdUserDto.getOutId());
            sortedMap.put("qrCodeNo", yMMemberDto.getQrcodeNo());
            sortedMap.put("qrCodeUrl", yMMemberDto.getQrcodeUrl());
            sortedMap.put("tradeNo", ymTradeDto.getTradeNo());
            sortedMap.put("amount", String.valueOf(ymTradeDto.getTransAmt()));
            sortedMap.put("type", ymTradeDto.getType().getCode());
            sortedMap.put("status", ymTradeDto.getStatus().getCode());
            sortedMap.put("bizCode", ymTradeDto.getBizCode().getCode());
            sortedMap.put("creates", DateHelper.formatDate(ymTradeDto.getCreateTs(), "yyyy-MM-dd HH:mm:ss"));
            String sign = SignUtil.createSign(sortedMap, ymThirdConfigDto.getKey());
            sortedMap.put("sign", sign);
            apiThirdService.thirdNotify(sortedMap, ymThirdConfigDto.getOrderNotifyUrl());
        }
        return ResultDtoFactory.toAck("获取成功", cloudCodePrepayOrderDto.getSdkParam());
    }

    /**
     * useragent分解
     *
     * @param userAgent
     * @return
     */
    public String routerUrl(String userAgent) {
        String[] arr = userAgent.split("\\*\\*\\*");
        String uri = "&";
        for (int i = 1; i < arr.length; i++) {
            if (StringUtils.indexOf(arr[i], EUserAgent.DEALER.getCode()) > 0) {
                continue;
            }
            StringBuilder sb = new StringBuilder();
            if (i < arr.length - 1) {
                uri = sb.append(uri).append(arr[i]).append("&").toString();
            } else {
                uri = sb.append(uri).append(arr[i]).toString();
            }
        }
        return uri;
    }

//    /**
//     * 元转换成分
//     *
//     * @param amount
//     * @return
//     */
//    public static String getMoney(String amount) {
//        if (amount == null) {
//            return "";
//        }
//        // 金额转化为分为单位
//        String currency = amount.replaceAll("\\$|\\￥|\\,", "");  //处理包含, ￥ 或者$的金额
//        int index = currency.indexOf(".");
//        int length = currency.length();
//        Long amLong = 0l;
//        if (index == -1) {
//            amLong = Long.valueOf(currency + "00");
//        } else if (length - index >= 3) {
//            amLong = Long.valueOf((currency.substring(0, index + 3)).replace(".", ""));
//        } else if (length - index == 2) {
//            amLong = Long.valueOf((currency.substring(0, index + 2)).replace(".", "") + 0);
//        } else {
//            amLong = Long.valueOf((currency.substring(0, index + 1)).replace(".", "") + "00");
//        }
//        return amLong.toString();
//    }

}
