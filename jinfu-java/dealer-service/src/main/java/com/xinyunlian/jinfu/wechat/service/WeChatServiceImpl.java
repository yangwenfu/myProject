package com.xinyunlian.jinfu.wechat.service;

import com.xinyunlian.jinfu.common.cache.constant.CacheType;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.common.util.OkHttpUtil;
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
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by menglei on 2017年08月01日.
 */
@Service
public class WeChatServiceImpl implements WeChatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeChatServiceImpl.class);

    @Autowired
    private RedisCacheManager redisCacheManager;

    @Value("${wechat.appid}")
    private String appId;

    @Value("${wechat.appsecret}")
    private String appsecret;

    private final static String
            API_WECHAT_URL = "https://api.weixin.qq.com",
            OPEN_WECHAT_URL = "https://open.weixin.qq.com",
            MP_WECHAT_URL = "https://mp.weixin.qq.com";

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
            result = OkHttpUtil.get(API_WECHAT_URL + "/cgi-bin/token?" + queryString, null, true);
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.DEALER_API_GET_ERROR, "获取access_token失败", e);
        }
        if (StringUtils.indexOf(result, "errcode") > 0) {
            throw new BizServiceException(EErrorCode.DEALER_API_GET_ERROR, "获取access_token失败:" + result);
        }
        JSONObject jSONObject = new JSONObject(result);
        token = jSONObject.get("access_token").toString();
        redisCacheManager.getCache(CacheType.WECHAT_ACCESS_TOKEN).put("access_token", token);
        return token;
    }

    /**
     * 创建永久二维码
     */
    @Override
    @Transactional
    public Map<String, String> createQrcode(String userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new BizServiceException(EErrorCode.DEALER_API_GET_ERROR, "创建永久二维码失败:userId不能为空");
        }
        String queryString = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"" + userId + "\"}}}";
        String result = null;
        try {
            String url = API_WECHAT_URL + "/cgi-bin/qrcode/create?access_token=" + getToken();
            result = OkHttpUtil.postJson(url, queryString, true);
        } catch (Exception e) {
            throw new BizServiceException(EErrorCode.DEALER_API_GET_ERROR, "创建永久二维码失败", e);
        }
        if (StringUtils.indexOf(result, "errcode") > 0) {
            throw new BizServiceException(EErrorCode.DEALER_API_GET_ERROR, "创建永久二维码失败:" + result);
        }
        JSONObject jSONObject = new JSONObject(result);
        Iterator iterator = jSONObject.keys();
        Map<String, String> resultMap = new HashMap<>();
        while (iterator.hasNext()) {
            String key = (String) iterator.next();
            String value = jSONObject.getString(key);
            resultMap.put(key, value);
        }
        return resultMap;
    }

    /**
     * 获取二维码图片地址
     *
     * @param userId
     * @return
     */
    @Override
    @Transactional
    public Map<String, String> getQrcodeInfo(String userId) {
        if (StringUtils.isEmpty(userId)) {
            throw new BizServiceException(EErrorCode.DEALER_API_GET_ERROR, "获取二维码图片地址失败:userId不能为空");
        }
        Map<String, String> qrcodeMap = createQrcode(userId);
        String queryString = null;
        try {
            queryString = URLEncoder.encode(qrcodeMap.get("ticket"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new BizServiceException(EErrorCode.DEALER_API_GET_ERROR, "获取二维码图片地址失败", e);
        }
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("qrCodeUrl", qrcodeMap.get("url"));
        resultMap.put("qrCodePic", MP_WECHAT_URL + "/cgi-bin/showqrcode?ticket=" + queryString);
        return resultMap;
    }

}
