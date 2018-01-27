package com.xinyunlian.jinfu.risk.dto.req;

import java.io.Serializable;

/**
 * Created by bright on 2016/11/16.
 */
public class CreditPhoneInfoReqDto extends CreditReqBaseDto{
    /* 证件号码 */
    private String idCard;

    /* 证件类型 */
    private String idType;

    /* 姓名 */
    private String name;

    /* 被调查的用户标识：手机号,IMSI,IMEI */
    private String contactMain;

    /* 联系人信息：手机号1,IMSI1,IMEI1,手机号2,IMSI2,IMEI2..... */
    private String contacts;

    /* 染黑度回溯时间: yyyy-mm-dd or yyyy/mm/dd */
    private String queryDate;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactMain() {
        return contactMain;
    }

    public void setContactMain(String contactMain) {
        this.contactMain = contactMain;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(String queryDate) {
        this.queryDate = queryDate;
    }
}
