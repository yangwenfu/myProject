package com.xinyunlian.jinfu.user.dto;

/**
 * Created by King on 2017/2/23.
 */

import java.io.Serializable;

public class CertifyDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userName;

    private String idCardNo;

    /**
     * 身份证正面
     */
    private String idCardFrontBase64;

    /**
     * 身份证背面
     */
    private String idCardBackBase64;

    /**
     * 手持身份证
     */
    private String heldIdCardBase64;

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

    public String getHeldIdCardBase64() {
        return heldIdCardBase64;
    }

    public void setHeldIdCardBase64(String heldIdCardBase64) {
        this.heldIdCardBase64 = heldIdCardBase64;
    }
}