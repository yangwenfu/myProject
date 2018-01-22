package com.xinyunlian.jinfu.risk.dto.req;

import java.io.Serializable;

/**
 * Created by bright on 2016/11/16.
 */
public class CreditBankCardAuthReqDto extends CreditReqBaseDto {
    /* 姓名 */
    private String name;

    /* 身份证号 */
    private String idCard;

    /* 证件类型 */
    private String idType;

    /* 银行卡号 */
    private String card;

    /* 金融机构名(如果是第三方征信平台使用，则此项必填) */
    private String orgName;

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

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
