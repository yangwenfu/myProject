package com.xinyunlian.jinfu.user.dto;

import java.io.Serializable;

/**
 * @author menglei
 */
public class CertificationDto implements Serializable{

    private String userName;

    private String idCardNo;

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
}
