package com.xinyunlian.jinfu.zrfundstx.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * Created by dongfangchao on 2016/11/21.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BaseResp implements Serializable {

    private static final long serialVersionUID = 2306334248555567008L;

    @XmlElement(name = "Version")
    private String version;

    @XmlElement(name = "MerchantId")
    private String merchantId;

    @XmlElement(name = "DistributorCode")
    private String distributorCode;

    @XmlElement(name = "BusinType")
    private String businType;

    @XmlElement(name = "ReturnCode")
    private String returnCode;

    @XmlElement(name = "ReturnMsg")
    private String returnMsg;

    @XmlElement(name = "Extension")
    private String extension;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getDistributorCode() {
        return distributorCode;
    }

    public void setDistributorCode(String distributorCode) {
        this.distributorCode = distributorCode;
    }

    public String getBusinType() {
        return businType;
    }

    public void setBusinType(String businType) {
        this.businType = businType;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
