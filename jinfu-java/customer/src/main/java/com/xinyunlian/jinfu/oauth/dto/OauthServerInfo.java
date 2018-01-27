package com.xinyunlian.jinfu.oauth.dto;

import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.OkHttpUtil;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JL on 2016/10/31.
 */
public abstract class OauthServerInfo {
    private static final Logger LOGGER = LoggerFactory.getLogger(OauthServerInfo.class);

    private final static String INDEX_PAGE = AppConfigUtil.getConfig("local_domain") + "/static/jinfu/html/my_jinfu.html#";
    private final static String ACTIVATE_PAGE = "/activate";
    private final static Map<String, String> TARGET_MAP = new HashMap<>();

    static {
        TARGET_MAP.put("1001", AppConfigUtil.getConfig("local_domain") + "/static/jinfu/html/my_jinfu.html#/insurance");
        TARGET_MAP.put("1002", AppConfigUtil.getConfig("local_domain") + "/static/jinfu/html/my_jinfu.html#/insurance_auto");
        TARGET_MAP.put("2001", AppConfigUtil.getConfig("local_domain") + "/static/jinfu/html/my_jinfu.html#/finance");
    }

    private String target;

    public OauthServerInfo(String target) {
        this.target = target;
    }

    /**
     * 获得目标跳转URL
     *
     * @return
     */
    public String getRedirectUrl() {
        String targetUrl = TARGET_MAP.get(target);
        if (StringUtils.isEmpty(targetUrl)) {
            targetUrl = INDEX_PAGE;
        }
        return targetUrl;
    }

    /**
     * 获得目标跳转激活地址
     *
     * @return
     */
    public String getActiveRedirectUrl() {
        return getRedirectUrl() + ACTIVATE_PAGE;
    }


    /**
     * 获得回调地址
     *
     * @return
     */
    public String getCallbackUrl() {
        if (StringUtils.isNotEmpty(target)) {
            return getCallbackUrlStr() + "?target=" + target;
        }
        return getCallbackUrlStr();
    }


    /**
     * 获得token
     *
     * @param code
     * @return
     * @throws IOException
     */
    public OauthToken getToken(String code) throws IOException {
        Map<String, String> param = new HashMap<>();
        param.put("client_id", this.getClientId());
        param.put("code", code);
        param.put("client_secret", this.getSecretKey());
        param.put("grant_type", "authorization_code");
        String tokenStr = OkHttpUtil.postForm(this.getServiceDomain() + this.getAccessTokenUrl(), param, true);
        LOGGER.info("tokenStr:" + tokenStr);
        return this.getOauthToken(tokenStr);
    }


    /**
     * 获得用户信息
     *
     * @param token
     * @return
     * @throws IOException
     */
    public StoreInfDto getUserProfile(OauthToken token) throws IOException {
        LOGGER.info("Token is " + token.getAccessToken());
        Map<String, String> param = new HashMap<>();
        param.put("access_token", token.getAccessToken());
        String userInfoStr = OkHttpUtil.postForm(this.getServiceDomain() + this.getUserProfileUrl(), param, true);
        LOGGER.info("User info string is :" + userInfoStr);
        return this.getStoreInfo(userInfoStr);
    }

    public abstract String getServiceDomain();

    public abstract String getClientId();

    public abstract String getSecretKey();

    public abstract String getAuthorizeUrl();

    public abstract String getAccessTokenUrl();

    public abstract String getUserProfileUrl();

    public abstract String getCallbackUrlStr();

    public abstract OauthToken getOauthToken(String tokenStr);

    public abstract StoreInfDto getStoreInfo(String userInfoStr);


}
