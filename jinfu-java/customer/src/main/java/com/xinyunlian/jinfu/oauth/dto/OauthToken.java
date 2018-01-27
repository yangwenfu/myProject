package com.xinyunlian.jinfu.oauth.dto;

import java.io.Serializable;

/**
 * Created by JL on 2016/10/27.
 */
public abstract class OauthToken implements Serializable {

    public abstract String getAccessToken();


}
