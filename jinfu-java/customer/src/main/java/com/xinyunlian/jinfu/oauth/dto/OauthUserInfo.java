package com.xinyunlian.jinfu.oauth.dto;

import com.xinyunlian.jinfu.store.dto.StoreInfDto;

import java.io.Serializable;

/**
 * Created by JL on 2016/10/27.
 */
public abstract class OauthUserInfo implements Serializable {

    public abstract StoreInfDto getStoreInfo();
}
