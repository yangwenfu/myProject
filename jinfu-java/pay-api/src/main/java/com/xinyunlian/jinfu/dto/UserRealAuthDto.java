package com.xinyunlian.jinfu.dto;

import java.io.Serializable;

/**
 * Created by JL on 2016/8/1.
 */
public class UserRealAuthDto implements Serializable {

    private String name;

    private String phone;

    private String idCardNo;

    private String bankCardNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

}
