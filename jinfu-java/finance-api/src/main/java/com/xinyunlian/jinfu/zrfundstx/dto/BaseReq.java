package com.xinyunlian.jinfu.zrfundstx.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2016/11/21.
 */
public class BaseReq implements Serializable {
    private static final long serialVersionUID = 4363668318067058052L;

    private String Version;

    private String MerchantId;

    private String DistributorCode;

    private String BusinType;

    private String Extension;

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getMerchantId() {
        return MerchantId;
    }

    public void setMerchantId(String merchantId) {
        MerchantId = merchantId;
    }

    public String getDistributorCode() {
        return DistributorCode;
    }

    public void setDistributorCode(String distributorCode) {
        DistributorCode = distributorCode;
    }

    public String getBusinType() {
        return BusinType;
    }

    public void setBusinType(String businType) {
        BusinType = businType;
    }

    public String getExtension() {
        return Extension;
    }

    public void setExtension(String extension) {
        Extension = extension;
    }
}
