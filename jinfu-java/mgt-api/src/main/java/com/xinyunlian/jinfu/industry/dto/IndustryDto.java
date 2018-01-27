package com.xinyunlian.jinfu.industry.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/5/17/0017.
 */
public class IndustryDto implements Serializable {

    private static final long serialVersionUID = 6073790296085985269L;

    private Long id;

    private String indName;

    private String indType;

    private String indDesc;

    private Boolean bizLicense;

    private Boolean storeLicence;

    private String licenceName;

    private String mcc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndName() {
        return indName;
    }

    public void setIndName(String indName) {
        this.indName = indName;
    }

    public Boolean getBizLicense() {
        return bizLicense;
    }

    public void setBizLicense(Boolean bizLicense) {
        this.bizLicense = bizLicense;
    }

    public String getLicenceName() {
        return licenceName;
    }

    public void setLicenceName(String licenceName) {
        this.licenceName = licenceName;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getIndType() {
        return indType;
    }

    public void setIndType(String indType) {
        this.indType = indType;
    }

    public String getIndDesc() {
        return indDesc;
    }

    public void setIndDesc(String indDesc) {
        this.indDesc = indDesc;
    }

    public Boolean getStoreLicence() {
        return storeLicence;
    }

    public void setStoreLicence(Boolean storeLicence) {
        this.storeLicence = storeLicence;
    }
}
