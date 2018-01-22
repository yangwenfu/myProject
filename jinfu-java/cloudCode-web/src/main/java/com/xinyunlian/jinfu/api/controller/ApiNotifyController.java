package com.xinyunlian.jinfu.api.controller;

import com.xinyunlian.jinfu.api.service.ApiNotifyService;
import com.xinyunlian.jinfu.api.service.ApiThirdService;
import com.xinyunlian.jinfu.api.validate.util.SignUtil;
import com.xinyunlian.jinfu.cashier.api.JinfuCashierSignature;
import com.xinyunlian.jinfu.clerk.dto.ClerkAuthDto;
import com.xinyunlian.jinfu.clerk.dto.ClerkInfDto;
import com.xinyunlian.jinfu.clerk.service.ClerkAuthService;
import com.xinyunlian.jinfu.clerk.service.ClerkService;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import com.xinyunlian.jinfu.store.service.StoreService;
import com.xinyunlian.jinfu.trade.dto.YmTradeDto;
import com.xinyunlian.jinfu.trade.enums.ETradeStatus;
import com.xinyunlian.jinfu.trade.service.YmTradeService;
import com.xinyunlian.jinfu.wechat.enums.EWeChatPushType;
import com.xinyunlian.jinfu.wechat.service.ApiWeChatService;
import com.xinyunlian.jinfu.yunma.dto.YMMemberDto;
import com.xinyunlian.jinfu.yunma.dto.YMUserInfoDto;
import com.xinyunlian.jinfu.yunma.dto.YmThirdConfigDto;
import com.xinyunlian.jinfu.yunma.dto.YmThirdUserDto;
import com.xinyunlian.jinfu.yunma.enums.EMemberAuditStatus;
import com.xinyunlian.jinfu.yunma.service.YMMemberService;
import com.xinyunlian.jinfu.yunma.service.YMUserInfoService;
import com.xinyunlian.jinfu.yunma.service.YmThirdConfigService;
import com.xinyunlian.jinfu.yunma.service.YmThirdUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by menglei on 2017年01月13日.
 */
@RestController
@RequestMapping(value = "open-api/notify")
public class ApiNotifyController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiNotifyController.class);

    @Autowired
    private ApiWeChatService apiWeChatService;
    @Autowired
    private YmTradeService ymTradeService;
    @Autowired
    private ClerkAuthService clerkAuthService;
    @Autowired
    private YMUserInfoService yMUserInfoService;
    @Autowired
    private YMMemberService yMMemberService;
    @Autowired
    private StoreService storeService;
    @Autowired
    private ClerkService clerkService;
    @Autowired
    private ApiNotifyService apiNotifyService;
    @Autowired
    private YmThirdUserService ymThirdUserService;
    @Autowired
    private YmThirdConfigService ymThirdConfigService;
    @Autowired
    private ApiThirdService apiThirdService;


    /**
     * 流水回调
     *
     * @return
     */
    @RequestMapping(value = "/trade_callback", method = RequestMethod.POST)
    public String trade_callback(HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Map<String, String> responseMap = new HashMap<>();
        Enumeration em = request.getParameterNames();
        while (em.hasMoreElements()) {
            String name = (String) em.nextElement();
            //String value = new String((request.getParameter(name).getBytes("ISO-8859-1")), "UTF-8");
            String value = request.getParameter(name);
            responseMap.put(name, value);
        }
        //验签
        if (!JinfuCashierSignature.verifySign(responseMap, AppConfigUtil.getConfig("cashier.key"), true)) {//验签失败
            LOGGER.error("验签失败", JsonUtil.toJson(responseMap));
            return "FAILED";
        }

        String resultCode = responseMap.get("resultCode");
        String resultMessage = responseMap.get("resultMessage");
        String payOrderNo = responseMap.get("payOrderNo"); // 收银台单号
        String outTradeNo = responseMap.get("outTradeNo"); // 云码单号
        String totalAmt = responseMap.get("totalAmt");   // 金额
        String timestamp = responseMap.get("timestamp");  // 时间搓
        String sign = responseMap.get("sign");       // 签名
        String signType = responseMap.get("signType");   // 签名类型
        String passbackParams = responseMap.get("passbackParams"); //
        String status = responseMap.get("status"); // SUCCESS 代表支付成功
        String payerAccount = responseMap.get("payerAccount"); // 付款帐号

        LOGGER.info("流水回调:" + JsonUtil.toJson(responseMap));

        YmTradeDto ymTradeDto = ymTradeService.findByTradeNo(outTradeNo);
        if (ymTradeDto == null) {
            LOGGER.error("流水不存在", outTradeNo);
            return "FAILED";
        }
        YMMemberDto yMMemberDto = yMMemberService.getMemberByMemberNo(ymTradeDto.getMemberNo());
        if (yMMemberDto == null) {
            LOGGER.error("云码店铺不存在", ymTradeDto.getMemberNo());
            return "FAILED";
        }

        if (ymTradeDto.getStatus().equals(ETradeStatus.UNPAID)) {//只更新，推送一次
            //更新订单
            ymTradeDto = new YmTradeDto();
            ymTradeDto.setTradeNo(outTradeNo);
            ETradeStatus tradeStatus = ETradeStatus.Fail;
            if ("SUCCESS".equals(status) || "SUCCESS".equals(resultCode)) {
                tradeStatus = ETradeStatus.SUCCESS;
            }
            ymTradeDto.setStatus(tradeStatus);
            ymTradeDto.setRespCode(resultCode);
            ymTradeDto.setRespInfo(resultMessage);
            YmTradeDto tradeDto = ymTradeService.updateTrade(ymTradeDto);
            if (tradeDto == null) {
                LOGGER.error("流水不存在", outTradeNo);
                return "FAILED";
            }
            ymTradeDto = ConverterService.convert(tradeDto, YmTradeDto.class);
            //推送消息
            StoreInfDto storeInfDto = storeService.findByStoreId(yMMemberDto.getStoreId());
            if (storeInfDto == null) {
                LOGGER.error("店铺不存在", yMMemberDto.getStoreId());
                return "FAILED";
            }
            if (ETradeStatus.SUCCESS.equals(ymTradeDto.getStatus())) {
                //推送店主
                YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByUserId(storeInfDto.getUserId());
                Map<String, String> map = new HashMap<>();
                map.put("openId", yMUserInfoDto.getOpenId());
                map.put("dateTime", DateHelper.formatDate(new Date(), "yyyy.MM.dd HH:mm"));
                map.put("orderNo", outTradeNo);
                map.put("price", tradeDto.getTransAmt() + StringUtils.EMPTY);
                map.put("storeName", storeInfDto.getStoreName());
                map.put("orderType", "");
                apiWeChatService.sendPush(map, EWeChatPushType.PAY);
                //推送店员
                List<ClerkAuthDto> list = clerkAuthService.findByStoreId(String.valueOf(storeInfDto.getStoreId()));
                List<String> clerkIds = new ArrayList<>();
                for (ClerkAuthDto clerkAuthDto : list) {
                    clerkIds.add(clerkAuthDto.getClerkId());
                }
                if (!CollectionUtils.isEmpty(clerkIds)) {
                    List<ClerkInfDto> clerkInfList = clerkService.findByClerkIds(clerkIds);
                    for (ClerkInfDto clerkInfDto : clerkInfList) {
                        map.put("openId", clerkInfDto.getOpenId());
                        apiWeChatService.sendPush(map, EWeChatPushType.PAY);
                    }
                }
            }
        }
        //支付状态回传(第三方) 是否需要多次回调？
        //String result = apiNotifyService.tradeCallback(ymTradeDto, yMMemberDto);
        //return result;

        //推送通知给合作方
        YmThirdUserDto ymThirdUserDto = ymThirdUserService.findByMemberId(yMMemberDto.getId());
        if (ymThirdUserDto != null) {
            YmThirdConfigDto ymThirdConfigDto = ymThirdConfigService.findById(ymThirdUserDto.getThirdConfigId());
            if (ymThirdConfigDto != null) {
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
                String thirdSign = SignUtil.createSign(sortedMap, ymThirdConfigDto.getKey());
                sortedMap.put("sign", thirdSign);
                apiThirdService.thirdNotify(sortedMap, ymThirdConfigDto.getNotifyUrl());
            }
        }
        return "SUCCESS";
    }

    /**
     * 店铺状态回调
     *
     * @return
     */
    @RequestMapping(value = "/member_callback", method = RequestMethod.POST)
    public String member_callback(HttpServletRequest request, HttpServletResponse response) {
        String memberNo;//接入方商户号
        String status;//状态
        String sign;//签名
        String charset;
        String signType;
        try {
            memberNo = new String(request.getParameter("memberNo").getBytes("ISO-8859-1"), "UTF-8");
            status = new String(request.getParameter("status").getBytes("ISO-8859-1"), "UTF-8");
            sign = new String(request.getParameter("sign").getBytes("ISO-8859-1"), "UTF-8");
            charset = new String(request.getParameter("charset").getBytes("ISO-8859-1"), "UTF-8");
            signType = new String(request.getParameter("signType").getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("获取失败", e);
            return "FAILED";
        }

        Map<String, String> responseMap = new HashMap<>();
        responseMap.put("memberNo", memberNo);
        responseMap.put("status", status);
        responseMap.put("sign", sign);
        responseMap.put("charset", charset);
        responseMap.put("signType", signType);

        if (!JinfuCashierSignature.verifySign(responseMap, AppConfigUtil.getConfig("cashier.key"))) {//验签失败
            LOGGER.error("验签失败", "memberNo=" + memberNo + "&status=" + status + "&sign=" + sign + "&charset=" + charset + "&signType=" + signType);
            return "FAILED";
        }

        YMMemberDto yMMemberDto = yMMemberService.getMemberByMemberNo(memberNo);
        if (yMMemberDto == null) {
            LOGGER.error("云码店铺不存在", memberNo);
            return "FAILED";
        } else if (!EMemberAuditStatus.AUDITING.equals(yMMemberDto.getMemberAuditStatus())) {
            LOGGER.error("云码店铺非审核中状态", yMMemberDto.getMemberNo());
            return "FAILED";
        }
        StoreInfDto storeInfDto = storeService.findByStoreId(yMMemberDto.getStoreId());
        if (storeInfDto == null) {
            LOGGER.error("店铺不存在", yMMemberDto.getStoreId());
            return "FAILED";
        }
        EMemberAuditStatus auditStatus = EMemberAuditStatus.SUCCESS;
        if ("0".equals(status)) {
            auditStatus = EMemberAuditStatus.FAILED;
        }
        yMMemberDto.setMemberAuditStatus(auditStatus);
        yMMemberService.updateMemberAuditStatus(yMMemberDto);
        Map<String, String> map = new HashMap<>();
        YMUserInfoDto yMUserInfoDto = yMUserInfoService.findByUserId(storeInfDto.getUserId());
        if (yMUserInfoDto != null && StringUtils.isNotEmpty(yMUserInfoDto.getOpenId())) {
            map.put("openId", yMUserInfoDto.getOpenId());
            map.put("storeName", storeInfDto.getStoreName());
            map.put("dateTime", DateHelper.formatDate(new Date(), "yyyy.MM.dd HH:mm"));
            map.put("remark", "您的云联云码已经可以使用。");
            map.put("title", "您提交云联云码申请现已审核通过。");
            map.put("auditmsg", "已通过");
            if ("0".equals(status)) {
                map.put("remark", "您可以联系客服：400 801 9906");
                map.put("title", "非常抱歉的通知您，您提交云联云码申请被拒绝。");
                map.put("auditmsg", "被拒绝");
            }
            apiWeChatService.sendPush(map, EWeChatPushType.AUDIT);
        }
        return "SUCCESS";
    }

    /**
     * 获取用户真实ip
     *
     * @param request
     * @return
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
