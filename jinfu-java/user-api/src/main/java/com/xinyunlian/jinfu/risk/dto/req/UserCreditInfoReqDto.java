package com.xinyunlian.jinfu.risk.dto.req;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bright on 2016/11/16.
 */
public class UserCreditInfoReqDto implements Serializable {
    private String userId;

    private String name;

    private String idCard;

    private String idType = "111";

    private String phone;

    private List<String> linkedContacts = new ArrayList<>();

    private String queryDate;

    private String bankCard;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getLinkedContacts() {
        return linkedContacts;
    }

    public void setLinkedContacts(List<String> linkedContacts) {
        this.linkedContacts = linkedContacts;
    }

    public String getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(String queryDate) {
        this.queryDate = queryDate;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }
}
