package com.xinyunlian.jinfu.oauth.dto;

import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.common.util.JsonUtil;
import com.xinyunlian.jinfu.common.util.OkHttpUtil;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jl062 on 2017/2/15.
 */
public class AioOauthServerInfo extends OauthServerInfo {
    private static final Logger LOGGER = LoggerFactory.getLogger(AioOauthServerInfo.class);

    private final static String AIO_DOMAIN =  AppConfigUtil.getConfig("aio_domain");
    private final static String AIO_CLIENT_ID =  AppConfigUtil.getConfig("aio_oauth_client_id");
    private final static String AIO_SECRET = AppConfigUtil.getConfig("aio_oauth_secret_key");
    private final static String AIO_AUTHORIZE = "/oauth/authorize";
    private final static String AIO_ACCESS_TOKEN = "/oauth/token";
    private final static String AIO_USER_PROFILE = "/oauth/get_user";
    private final static String AIO_CALLBACK_URL = AppConfigUtil.getConfig("local_domain") + "/jinfu/web/oauthClient/callback_aio";

    @Override
    public String getServiceDomain() {
        return AIO_DOMAIN;
    }

    @Override
    public String getClientId() {
        return AIO_CLIENT_ID;
    }

    @Override
    public String getSecretKey() {
        return AIO_SECRET;
    }

    @Override
    public String getAuthorizeUrl() {
        return AIO_AUTHORIZE;
    }

    @Override
    public String getAccessTokenUrl() {
        return AIO_ACCESS_TOKEN;
    }

    @Override
    public String getUserProfileUrl() {
        return AIO_USER_PROFILE;
    }

    @Override
    public String getCallbackUrlStr() {
        return AIO_CALLBACK_URL;
    }


    public AioOauthServerInfo(String target) {
        super(target);
    }

    @Override
    public OauthToken getOauthToken(String tokenStr) {
        return JsonUtil.toObject(AioOauthToken.class, tokenStr);
    }

    @Override
    public StoreInfDto getStoreInfo(String userInfoStr) {
        return ((AioUserInfoDto) JsonUtil.toObject(AioUserInfoDto.class, userInfoStr)).getStoreInfo();
    }

    @Override
    public OauthToken getToken(String code) throws IOException {
        Map<String, String> param = new HashMap<>();
        param.put("client_id", this.getClientId());
        param.put("code", code);
        param.put("client_secret", this.getSecretKey());
        param.put("grant_type", "authorization_code");
        param.put("redirect_uri", this.getCallbackUrl());
        String tokenStr = OkHttpUtil.postForm(this.getServiceDomain() + this.getAccessTokenUrl(), param, true);
        LOGGER.info("tokenStr:" + tokenStr);
        return this.getOauthToken(tokenStr);
    }
}
