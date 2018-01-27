package com.xinyunlian.jinfu.oauth.dto;

import com.xinyunlian.jinfu.common.util.JSONParser;
import com.xinyunlian.jinfu.common.util.JsonUtil;

/**
 * Created by JL on 2016/11/4.
 */
public class B2bOauthToken extends OauthToken {

    private static final long serialVersionUID = 1L;

    private String expires_in;
    private String access_token;
    private String error;
    private String error_description;

    public B2bOauthToken() {

    }

    public B2bOauthToken(String expiresIn, String access_token, String error, String errorDescription) {
        this.expires_in = expiresIn;
        this.access_token = access_token;
        this.error = error;
        this.error_description = errorDescription;
    }

    public B2bOauthToken(String access_token, String error, String errorDescription) {
        this.expires_in = "3600";
        this.access_token = access_token;
        this.error = error;
        this.error_description = errorDescription;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    @Override
    public String getAccessToken() {
        return access_token;
    }


    public static void main(String args[]) {
        String o = "\"{\\\"access_token\\\":\\\"af01f7ff6670f168e1b52b05c877da78\\\",\\\"expires_in\\\":300}\"";
        JSONParser jsonParser = new JSONParser(o);
        B2bOauthToken a =  JsonUtil.toObject(B2bOauthToken.class, jsonParser.parse().toString());
        System.out.print(a.getAccess_token());
    }
}
