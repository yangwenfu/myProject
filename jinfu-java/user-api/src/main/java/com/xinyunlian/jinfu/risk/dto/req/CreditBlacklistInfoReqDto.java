package com.xinyunlian.jinfu.risk.dto.req;

import java.io.Serializable;

/**
 * Created by bright on 2016/11/16.
 */
public class CreditBlacklistInfoReqDto extends CreditReqBaseDto {

    /* 手机号 */
    private String phone;

    /* 姓名 */
    private String name;

    /* 身份证号 */
    private String idCard;

    /* 银行 */
    private String bank;

    /* 银行卡号 */
    private String card;

    /* 地区区号(如武汉027，北京010，上海021) */
    private String areaCode;

    /* imsi */
    private String imsi;

    /* imei */
    private String imei;

    /* 金融机构名(如果是第三方征信平台使用，则此项必填) */
    private String orgName;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
