package com.xinyunlian.jinfu.pingan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/6/16/0016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OauthTokenDataResponse implements Serializable {
    private static final long serialVersionUID = -7930318428162620910L;

    @JsonProperty("expires_in")
    private String expiresIn;
    @JsonProperty("openid")
    private String openid;
    @JsonProperty("access_token")
    private String accessToken;

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
