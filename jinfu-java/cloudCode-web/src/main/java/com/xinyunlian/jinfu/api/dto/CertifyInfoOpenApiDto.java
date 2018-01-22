package com.xinyunlian.jinfu.api.dto;

import com.xinyunlian.jinfu.api.validate.dto.OpenApiBaseDto;

/**
 * Created by KimLL on 2016/8/18.
 */

public class CertifyInfoOpenApiDto extends OpenApiBaseDto {
    private static final long serialVersionUID = 1L;
    private String userId;

    private String mobile;

    private String bankMobile;

    private String userName;

    private String idCardNo;

    private String bankCardNo;

    private String idCardFrontBase64;

    private String idCardBackBase64;

    private Long bankId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
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

    public String getBankMobile() {
        return bankMobile;
    }

    public void setBankMobile(String bankMobile) {
        this.bankMobile = bankMobile;
    }

    @Override
    public String toString() {
        return "CertifyInfoOpenApiDto{" +
                "userId='" + userId + '\'' +
                ", mobile='" + mobile + '\'' +
                ", bankMobile='" + bankMobile + '\'' +
                ", userName='" + userName + '\'' +
                ", idCardNo='" + idCardNo + '\'' +
                ", bankCardNo='" + bankCardNo + '\'' +
                ", idCardFrontBase64='" + idCardFrontBase64 + '\'' +
                ", idCardBackBase64='" + idCardBackBase64 + '\'' +
                ", bankId=" + bankId +
                '}';
    }
}
