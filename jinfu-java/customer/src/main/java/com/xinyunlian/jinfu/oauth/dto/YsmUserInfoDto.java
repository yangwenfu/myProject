package com.xinyunlian.jinfu.oauth.dto;

import com.xinyunlian.jinfu.store.dto.StoreInfDto;

/**
 * Created by JL on 2016/11/4.
 */
public class YsmUserInfoDto extends OauthUserInfo {

    private static final long serialVersionUID = 1L;

    private String flag;
    private String msg;
    private B2bUserInfoDto data;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public B2bUserInfoDto getData() {
        return data;
    }

    public void setData(B2bUserInfoDto data) {
        this.data = data;
    }

    @Override
    public StoreInfDto getStoreInfo() {
        return data.getStoreInfo();
    }
}
