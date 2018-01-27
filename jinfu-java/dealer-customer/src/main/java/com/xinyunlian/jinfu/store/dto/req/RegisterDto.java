package com.xinyunlian.jinfu.store.dto.req;

import java.io.Serializable;

/**
 * Created by menglei on 2016年09月05日.
 */

public class RegisterDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String mobile;

    private String userName;

    private String idCardNo;

    private String loginPassword;

    private String verifyCode;

    private String logLng;

    private String logLat;

    private String logAddress;

    /**
     * 身份证正面
     */
    private String idCardFrontBase64;

    /**
     * 身份证背面
     */
    private String idCardBackBase64;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getLogLng() {
        return logLng;
    }

    public void setLogLng(String logLng) {
        this.logLng = logLng;
    }

    public String getLogLat() {
        return logLat;
    }

    public void setLogLat(String logLat) {
        this.logLat = logLat;
    }

    public String getLogAddress() {
        return logAddress;
    }

    public void setLogAddress(String logAddress) {
        this.logAddress = logAddress;
    }

    public String getIdCardFrontBase64() {
        return idCardFrontBase64;
    }

    public void setIdCardFrontBase64(String idCardFrontBase64) {
        this.idCardFrontBase64 = idCardFrontBase64;
    }

    public String getIdCardBackBase64() {
        return idCardBackBase64;
    }

    public void setIdCardBackBase64(String idCardBackBase64) {
        this.idCardBackBase64 = idCardBackBase64;
    }
}
