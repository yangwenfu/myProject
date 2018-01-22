package com.xinyunlian.jinfu.oauth.dto;

import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.JSONParser;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;

/**
 * 新商盟B2B OAUTH认证客户端信息
 * Created by JL on 2016/10/31.
 */
public class B2BOauthServerInfo extends OauthServerInfo {

    private final static String B2B_DOMAIN = AppConfigUtil.getConfig("b2b_domain");
    private final static String B2B_CLIENT_ID = AppConfigUtil.getConfig("b2b_oauth_client_id");
    private final static String B2B_SECRET = AppConfigUtil.getConfig("b2b_oauth_secret_key");
    private final static String B2B_AUTHORIZE = "/xinyunlian-ecom/oauth/authorize.jhtml";
    private final static String B2B_ACCESS_TOKEN = "/xinyunlian-ecom/oauth/access_token.jhtml";
    private final static String B2B_USER_PROFILE = "/xinyunlian-ecom/oauth/get_user.jhtml";
    private final static String B2B_CALLBACK_URL = AppConfigUtil.getConfig("local_domain") + "/jinfu/web/oauthClient/callback_xsm";

    public B2BOauthServerInfo() {
        super(null);
    }

    public B2BOauthServerInfo(String target) {
        super(target);
    }

    @Override
    public String getServiceDomain() {
        return B2B_DOMAIN;
    }

    @Override
    public String getClientId() {
        return B2B_CLIENT_ID;
    }

    @Override
    public String getSecretKey() {
        return B2B_SECRET;
    }

    @Override
    public String getAuthorizeUrl() {
        return B2B_AUTHORIZE;
    }

    @Override
    public String getAccessTokenUrl() {
        return B2B_ACCESS_TOKEN;
    }

    @Override
    public String getUserProfileUrl() {
        return B2B_USER_PROFILE;
    }

    @Override
    public String getCallbackUrlStr() {
        return B2B_CALLBACK_URL;
    }

    @Override
    public OauthToken getOauthToken(String tokenStr) {
        JSONParser jsonParser = new JSONParser(tokenStr);
        return JsonUtil.toObject(B2bOauthToken.class, jsonParser.parse().toString());
    }

    @Override
    public StoreInfDto getStoreInfo(String userInfoStr) {
        JSONParser jsonParser = new JSONParser(userInfoStr);
        return ((B2bUserInfoDto) JsonUtil.toObject(B2bUserInfoDto.class, jsonParser.parse().toString())).getStoreInfo();
    }
}

