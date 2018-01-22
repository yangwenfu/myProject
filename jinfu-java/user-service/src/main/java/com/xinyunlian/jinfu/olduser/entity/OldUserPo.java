package com.xinyunlian.jinfu.olduser.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by DongFC on 2016-11-07.
 */
@Entity
@Table(name = "old_user")
public class OldUserPo implements Serializable{

    private static final long serialVersionUID = -6213112602187177971L;

    @Id
    @Column(name = "USER_ID")
    @GenericGenerator(name="idGenerator", strategy="uuid")
    @GeneratedValue(generator="idGenerator")
    private String userId;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "ID_CARD_NO")
    private String idCardNo;

    @Column(name = "IDENTITY_AUTH")
    private Boolean identityAuth;

    @Column(name = "IDENTITY_AUTH_DATE")
    private Date identityAuthDate;

    @Column(name = "STORE_AUTH")
    private Boolean storeAuth;

    @Column(name = "SOURCE")
    private String source;

    @Column(name = "TOBACCO_CERTIFICATE_NO")
    private String tobaccoCertificateNo;

    @Column(name="BANK_CARD_NO")
    private String bankCardNo;

    @Column(name="BANK_CARD_NAME")
    private String bankCardName;

    @Column(name="MOBILE_NO")
    private String mobileNo;

    @Column(name="BANK_CNAPS_CODE")
    private String bankCnapsCode;

    @Column(name="BANK_CODE")
    private String bankCode;

    @Column(name="BANK_NAME")
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
