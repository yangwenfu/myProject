package com.xinyunlian.jinfu.user.dto.req;

import java.io.Serializable;

/**
 * Created by KimLL on 2016/8/18.
 */

public class CertifyInfoDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userId;

    private String mobile;

    private String userName;

    private String idCardNo;

    private String bankCardNo;

    private Long bankId;

    private String idCardFrontPicBase64;//身份证正面

    private String idCardBackPicBase64;//身份证反面

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

    public String getIdCardFrontPicBase64() {
        return idCardFrontPicBase64;
    }

    public void setIdCardFrontPicBase64(String idCardFrontPicBase64) {
        this.idCardFrontPicBase64 = idCardFrontPicBase64;
    }

    public String getIdCardBackPicBase64() {
        return idCardBackPicBase64;
    }

    public void setIdCardBackPicBase64(String idCardBackPicBase64) {
        this.idCardBackPicBase64 = idCardBackPicBase64;
    }
}
