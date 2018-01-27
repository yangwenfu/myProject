package com.xinyunlian.jinfu.wechat.service;

import com.xinyunlian.jinfu.common.cache.constant.CacheType;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.HttpUtil;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.RandomUtil;
import com.xinyunlian.jinfu.common.util.SHA1;
import com.xinyunlian.jinfu.trade.enums.EBizCode;
import com.xinyunlian.jinfu.wechat.dto.JsApiDto;
import com.xinyunlian.jinfu.wechat.dto.TemplateData;
import com.xinyunlian.jinfu.wechat.dto.WxTemplate;
import com.xinyunlian.jinfu.wechat.enums.EWeChatPushType;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by menglei on 2016年12月29日.
 */
@Service
public class ApiWeChatServiceImpl implements ApiWeChatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiWeChatServiceImpl.class);

    @Autowired
    private RedisCacheManager redisCacheManager;

    @Value("${wechat.appid}")
    private String appId;

    @Value("${wechat.appsecret}")
    private String appsecret;

    @Value("${wechat.push.templateid.pay}")
    private String templateid_pay;

    @Value("${wechat.push.templateid.auth}")
    private String templateid_auth;

    @Value("${wechat.push.templateid.apply}")
    private String templateid_apply;

    @Value("${wechat.push.templateid.audit}")
    private String templateid_audit;

    @Value("${yunma.url}")
    private String YUNMA_URL;

    private String wxTemplateColor = "#0000FF";

    private final static String
            API_WECHAT_URL = "https://api.weixin.qq.com",
            OPEN_WECHAT_URL = "https://open.weixin.qq.com";

    /**
     * 获取access token
     *
     * @return
     */
    private String getToken() {
        String token = redisCacheManager.getCache(CacheType.WECHAT_ACCESS_TOKEN).get("access_token", String.class);
        if (StringUtils.isNotEmpty(token)) {
            return token;
        }
        String queryString = null;
        String result = null;
        try {
            queryString = "grant_type=client_credential&appid=" + appId + "&secret=" + appsecret;
            result = HttpUtil.doGetToString(API_WECHAT_URL + "/cgi-bin/token?" + queryString);
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "获取access_token失败", e);
        }
        if (StringUtils.indexOf(result, "errcode") > 0) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "获取access_token失败:" + result);
        }
        JSONObject jSONObject = new JSONObject(result);
        token = jSONObject.get("access_token").toString();
        redisCacheManager.getCache(CacheType.WECHAT_ACCESS_TOKEN).put("access_token", token);
        return token;
    }

    /**
     * 获取jsapi ticket
     *
     * @return
     */
    private String getJsApiTicket() {
        String jsapi_ticket = redisCacheManager.getCache(CacheType.WECHAT_JSAPI_TICKET).get("jsapi_ticket", String.class);
        if (StringUtils.isNotEmpty(jsapi_ticket)) {
            return jsapi_ticket;
        }
        String queryString = null;
        String result = null;
        try {
            queryString = "access_token=" + getToken() + "&type=jsapi";
            result = HttpUtil.doGetToString(API_WECHAT_URL + "/cgi-bin/ticket/getticket?" + queryString);
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "获取access_token失败", e);
        }
        JSONObject jSONObject = new JSONObject(result);
        if (!"0".equals(jSONObject.get("errcode").toString())) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "获取access_token失败:" + result);
        }
        jsapi_ticket = jSONObject.get("ticket").toString();
        redisCacheManager.getCache(CacheType.WECHAT_JSAPI_TICKET).put("jsapi_ticket", jsapi_ticket);
        return jsapi_ticket;
    }

    /**
     * 获取 js sdk 参数
     * @param url
     * @return
     */
    @Override
    @Transactional
    public JsApiDto getJsApi(String url) {
        JsApiDto jsApiDto = new JsApiDto();
        jsApiDto.setAppId(appId);
        jsApiDto.setTimestamp(String.valueOf(System.currentTimeMillis()).substring(0, 10));
        jsApiDto.setNonceStr(RandomUtil.getMixStr(20));
        String queryString = "jsapi_ticket=" + getJsApiTicket() + "&noncestr=" + jsApiDto.getNonceStr() + "&timestamp=" + jsApiDto.getTimestamp() + "&url=" + url.replace("&amp;", "&").replace("&#61;", "=");
        jsApiDto.setSignature(new SHA1().getDigestOfString(queryString.getBytes()));
        return jsApiDto;
    }

    /**
     * 支付消息推送
     *
     * @param paramsMap openId,orderNo,price,storeName,datetime,orderType
     * @param type      pay apply auth
     */
    @Override
    @Transactional
    public void sendPush(Map<String, String> paramsMap, EWeChatPushType type) {
        if (StringUtils.isEmpty(paramsMap.get("openId"))) {
            return;
        }
        String result = null;
        try {
            WxTemplate t;
            if (EWeChatPushType.PAY.equals(type)) {
                t = payTemplate(paramsMap);
            } else if (EWeChatPushType.APPLY.equals(type)) {
                t = applyTemplate(paramsMap);
            } else if (EWeChatPushType.AUTH.equals(type)) {
                t = authTemplate(paramsMap);
            } else if (EWeChatPushType.AUDIT.equals(type)) {
                t = auditTemplate(paramsMap);
            } else {
                return;
            }
            result = HttpUtil.doPost(API_WECHAT_URL + "/cgi-bin/message/template/send?access_token=" + getToken(), "application/json; charset=UTF-8", JsonUtil.toJson(t).toString());
            result = URLDecoder.decode(result, "UTF-8");
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "支付消息推送失败", e);
        }
        JSONObject jSONObject = new JSONObject(result);
        if (!"0".equals(jSONObject.get("errcode").toString())) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "支付消息推送失败:" + result);
        }
    }

    /**
     * 支付消息模版
     *
     * @param paramsMap openId,orderNo,price,storeName,datetime,orderType
     * @return
     */
    private WxTemplate payTemplate(Map<String, String> paramsMap) {
        WxTemplate t = new WxTemplate();
        t.setTouser(paramsMap.get("openId"));
        t.setTemplate_id(templateid_pay);
        t.setUrl(YUNMA_URL + "/static/cloudCode/html/bill_record.html");
        Map<String, TemplateData> m = new HashMap<>();
        TemplateData first = new TemplateData("尊敬的商户会员：" + paramsMap.get("storeName") + "\n\n您有一笔交易支付成功", StringUtils.EMPTY);
        m.put("first", first);
        TemplateData keyword1 = new TemplateData(paramsMap.get("orderNo"), StringUtils.EMPTY);
        m.put("keyword1", keyword1);
        TemplateData keyword2 = new TemplateData(paramsMap.get("dateTime"), StringUtils.EMPTY);
        m.put("keyword2", keyword2);
        String priceMsg = "人民币";
        if (EBizCode.CMCC.getCode().equals(paramsMap.get("orderType"))) {
            priceMsg = "中移电子券抵扣";
        }
        TemplateData keyword3 = new TemplateData(priceMsg + paramsMap.get("price") + "元", wxTemplateColor);
        m.put("keyword3", keyword3);
        String remarkMess = "\n★如有问题，请联系我们！★";
        TemplateData remark = new TemplateData(remarkMess, StringUtils.EMPTY);
        m.put("remark", remark);
        t.setData(m);
        return t;
    }

    /**
     * 申请消息模版
     *
     * @param paramsMap openId,name,mobile,datetime
     * @return
     */
    private WxTemplate applyTemplate(Map<String, String> paramsMap) {
        WxTemplate t = new WxTemplate();
        t.setTouser(paramsMap.get("openId"));
        t.setTemplate_id(templateid_apply);
        t.setUrl(YUNMA_URL + "/static/cloudCode/html/AssistantAuth.html");
        Map<String, TemplateData> m = new HashMap<>();
        TemplateData first = new TemplateData("您收到了来自收银员" + paramsMap.get("name") + "的授权请求", StringUtils.EMPTY);
        m.put("first", first);
        TemplateData keyword1 = new TemplateData(paramsMap.get("name"), StringUtils.EMPTY);
        m.put("keyword1", keyword1);
        TemplateData keyword2 = new TemplateData(paramsMap.get("mobile"), StringUtils.EMPTY);
        m.put("keyword2", keyword2);
        TemplateData keyword3 = new TemplateData(paramsMap.get("dateTime"), wxTemplateColor);
        m.put("keyword3", keyword3);
        String remarkMess = "\n★如有问题，请联系我们！★";
        TemplateData remark = new TemplateData(remarkMess, StringUtils.EMPTY);
        m.put("remark", remark);
        t.setData(m);
        return t;
    }

    /**
     * 授权消息模版
     *
     * @param paramsMap openId,datetime,message
     * @return
     */
    private WxTemplate authTemplate(Map<String, String> paramsMap) {
        WxTemplate t = new WxTemplate();
        t.setTouser(paramsMap.get("openId"));
        t.setTemplate_id(templateid_auth);
        t.setUrl(YUNMA_URL + "/static/cloudCode/html/AssistantAuth.html");
        Map<String, TemplateData> m = new HashMap<>();
        TemplateData first = new TemplateData("您的请求已被处理", StringUtils.EMPTY);
        m.put("first", first);
        TemplateData keyword1 = new TemplateData(paramsMap.get("dateTime"), StringUtils.EMPTY);
        m.put("keyword1", keyword1);
        TemplateData keyword2 = new TemplateData(paramsMap.get("message"), StringUtils.EMPTY);
        m.put("keyword2", keyword2);
        String remarkMess = "\n★如有问题，请联系我们！★";
        TemplateData remark = new TemplateData(remarkMess, StringUtils.EMPTY);
        m.put("remark", remark);
        t.setData(m);
        return t;
    }

    /**
     * 审核结果通知消息模版
     *
     * @param paramsMap openId,title,storeName,auditmsg,remark,datetime
     * @return
     */
    private WxTemplate auditTemplate(Map<String, String> paramsMap) {
        WxTemplate t = new WxTemplate();
        t.setTouser(paramsMap.get("openId"));
        t.setTemplate_id(templateid_audit);
        t.setUrl("");
        Map<String, TemplateData> m = new HashMap<>();
        TemplateData first = new TemplateData("尊敬的金服会员。\n" + paramsMap.get("title") + "\n", StringUtils.EMPTY);
        m.put("first", first);
        TemplateData keyword1 = new TemplateData(paramsMap.get("storeName"), StringUtils.EMPTY);
        m.put("keyword1", keyword1);
        TemplateData keyword2 = new TemplateData(paramsMap.get("auditmsg"), StringUtils.EMPTY);
        m.put("keyword2", keyword2);
        TemplateData keyword3 = new TemplateData(paramsMap.get("dateTime"), wxTemplateColor);
        m.put("keyword3", keyword3);
        TemplateData remark = new TemplateData("\n" + paramsMap.get("remark"), StringUtils.EMPTY);
        m.put("remark", remark);
        t.setData(m);
        return t;
    }

    /**
     * 获取code的url，下一步获取openid
     */
    @Override
    @Transactional
    public String getAuthCodeUrl(String uri, String state) {
        String auth_url = null;
        try {
            auth_url = OPEN_WECHAT_URL + "/connect/oauth2/authorize?"
                    + "appid=" + appId + "&redirect_uri=" + URLEncoder.encode(uri, "UTF-8") + "&response_type=code&scope=snsapi_base&state=" + URLEncoder.encode(state, "UTF-8")
                    + "#wechat_redirect";
        } catch (UnsupportedEncodingException e) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "获取code的url失败", e);
        }
        return auth_url;
    }

    /**
     * 通过code获取openid
     */
    @Override
    @Transactional
    public String getAuthOpenid(String code) {
        if (StringUtils.isEmpty(code)) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "通过code获取openid失败:code不能为空");
        }
        String queryString = null;
        String result = null;
        try {
            queryString = "appid=" + appId + "&secret=" + appsecret + "&code=" + code + "&grant_type=authorization_code";
            result = HttpUtil.doGetToString(API_WECHAT_URL + "/sns/oauth2/access_token?" + queryString);
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "通过code获取openid失败", e);
        }
        if (StringUtils.indexOf(result, "errcode") > 0) {
            throw new BizServiceException(EErrorCode.CLOUDCODE_API_GET_ERROR, "通过code获取openid失败:" + result);
        }
        JSONObject jSONObject = new JSONObject(result);
        return jSONObject.get("openid").toString();
    }

}
