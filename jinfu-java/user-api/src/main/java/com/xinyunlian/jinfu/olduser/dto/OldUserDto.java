package com.xinyunlian.jinfu.olduser.dto;

import com.xinyunlian.jinfu.store.dto.StoreInfDto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by DongFC on 2016-11-07.
 */
public class OldUserDto implements Serializable{

    private static final long serialVersionUID = 4611259011286891827L;
    private String userId;

    private String mobile;

    private String userName;

    private String idCardNo;

    private Boolean identityAuth;

    private Date identityAuthDate;

    private Boolean storeAuth;

    private String source;

    private String tobaccoCertificateNo;

    private StoreInfDto storeInfDto;

    private String bankCardNo;

    private String bankCardName;

    private String mobileNo;

    private String bankCnapsCode;

    private String bankCode;

    private String bankName;

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public Boolean getIdentityAuth() {
        return identityAuth;
    }

    public void setIdentityAuth(Boolean identityAuth) {
        this.identityAuth = identityAuth;
    }

    public Date getIdentityAuthDate() {
        return identityAuthDate;
    }

    public void setIdentityAuthDate(Date identityAuthDate) {
        this.identityAuthDate = identityAuthDate;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Boolean getStoreAuth() {
        return storeAuth;
    }

    public void setStoreAuth(Boolean storeAuth) {
        this.storeAuth = storeAuth;
    }

    public String getTobaccoCertificateNo() {
        return tobaccoCertificateNo;
    }

    public void setTobaccoCertificateNo(String tobaccoCertificateNo) {
        this.tobaccoCertificateNo = tobaccoCertificateNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public StoreInfDto getStoreInfDto() {
        return storeInfDto;
    }

    public void setStoreInfDto(StoreInfDto storeInfDto) {
        this.storeInfDto = storeInfDto;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBankCardName() {
        return bankCardName;
    }

    public void setBankCardName(String bankCardName) {
        this.bankCardName = bankCardName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getBankCnapsCode() {
        return bankCnapsCode;
    }

    public void setBankCnapsCode(String bankCnapsCode) {
        this.bankCnapsCode = bankCnapsCode;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
