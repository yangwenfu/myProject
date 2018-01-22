package com.xinyunlian.jinfu.user.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2017年01月04日.
 */
public class MobileDto implements Serializable {

    private static final long serialVersionUID = -1L;

    private String mobile;

    private String verifyCode;

    private String storeId;

    private String codeType;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }
}
