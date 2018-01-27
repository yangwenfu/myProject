package com.xinyunlian.jinfu.risk.dto.req;

import java.io.Serializable;

/**
 * Created by bright on 2016/11/16.
 */
public class CreditLoanInfoReqDto extends CreditReqBaseDto {
    /* 手机号 */
    private String phone;

    /* 身份证号 */
    private String idCard;

    /* 姓名 */
    private String name;

    /* imsi */
    private String imsi;

    /* imei */
    private String imei;

    /* 金融机构名(如果是第三方征信平台使用，则此项必填) */
    private String orgName;

    /* 回溯时间 */
    private String queryDate;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getQueryDate() {
        return queryDate;
    }

    public void setQueryDate(String queryDate) {
        this.queryDate = queryDate;
    }
}
