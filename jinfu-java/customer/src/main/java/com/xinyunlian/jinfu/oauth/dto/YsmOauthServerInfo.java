package com.xinyunlian.jinfu.oauth.dto;

import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;

/**
 * 陕西易商盟OAUTH认证客户端信息
 * <p>
 * Created by JL on 2016/10/31.
 */
public class YsmOauthServerInfo extends OauthServerInfo {

    private final static String SX_DOMAIN = AppConfigUtil.getConfig("sx_domain");
    private final static String SX_CLIENT_ID = AppConfigUtil.getConfig("sx_oauth_client_id");
    private final static String SX_SECRET = AppConfigUtil.getConfig("sx_oauth_secret_key");
    private final static String SX_AUTHORIZE = "/oauth/authorize";
    private final static String SX_ACCESS_TOKEN = "/oauth/access_token";
    private final static String SX_USER_PROFILE = "/shop/shop_info";
    private final static String SX_CALLBACK_URL = AppConfigUtil.getConfig("local_domain") + "/jinfu/web/oauthClient/callback_ysm";

    public YsmOauthServerInfo(String target) {
        super(target);
    }

    @Override
    public String getServiceDomain() {
        return SX_DOMAIN;
    }

    @Override
    public String getClientId() {
        return SX_CLIENT_ID;
    }

    @Override
    public String getSecretKey() {
        return SX_SECRET;
    }

    @Override
    public String getAuthorizeUrl() {
        return SX_AUTHORIZE;
    }

    @Override
    public String getAccessTokenUrl() {
        return SX_ACCESS_TOKEN;
    }

    @Override
    public String getUserProfileUrl() {
        return SX_USER_PROFILE;
    }

    @Override
    public String getCallbackUrlStr() {
        return SX_CALLBACK_URL;
    }

    @Override
    public OauthToken getOauthToken(String tokenStr) {
        return JsonUtil.toObject(YsmOauthToken.class, tokenStr);
    }

    @Override
    public StoreInfDto getStoreInfo(String userInfoStr) {
        return ((YsmUserInfoDto) JsonUtil.toObject(YsmUserInfoDto.class, userInfoStr)).getStoreInfo();
    }
}
