package com.xinyunlian.jinfu.risk.dto.req;

import java.io.Serializable;

/**
 * Created by bright on 2016/11/16.
 */
public class CreditIdCheckReqDto extends CreditReqBaseDto {
    /* 姓名 */
    private String name;

    /* 身份证号 */
    private String idCard;

    /* 查询类型（默认是基本信息）： 0-基本信息;1-基本信息加地址；2-基本信息加证件照；3-基本信息加地址加证件照 */
    private String licenseType = "3";

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

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
