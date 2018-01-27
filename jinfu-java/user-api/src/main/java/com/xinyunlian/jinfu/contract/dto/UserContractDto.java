package com.xinyunlian.jinfu.contract.dto;

import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import com.xinyunlian.jinfu.contract.enums.ESignedMark;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by JL on 2016/9/20.
 */
public class UserContractDto implements Serializable {

    private static final long serialVersionUID = 2844667446678407461L;
    private String userId;

    private String signName;

    private String prodId;

    private String prodName;

    private String bizId;

    private ECntrctTmpltType templateType;

    private Map<String, String> model = new HashMap<>();

    private String content;

    private String filePath;

    private String bsSignid;

    private String bsDocid;

    private String bsVatecode;

    private String contractName;

    private String contractId;

    private Boolean signed;

    private ESignedMark signedMark;

    public ESignedMark getSignedMark() {
        return signedMark;
    }

    public void setSignedMark(ESignedMark signedMark) {
        this.signedMark = signedMark;
    }

    public Boolean getSigned() {
        return signed;
    }

    public void setSigned(Boolean signed) {
        this.signed = signed;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public ECntrctTmpltType getTemplateType() {
        return templateType;
    }

    public void setTemplateType(ECntrctTmpltType templateType) {
        this.templateType = templateType;
    }

    public Map<String, String> getModel() {
        return model;
    }

    public void setModel(Map<String, String> model) {
        this.model = model;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getBsSignid() {
        return bsSignid;
    }

    public void setBsSignid(String bsSignid) {
        this.bsSignid = bsSignid;
    }

    public String getBsDocid() {
        return bsDocid;
    }

    public void setBsDocid(String bsDocid) {
        this.bsDocid = bsDocid;
    }

    public String getBsVatecode() {
        return bsVatecode;
    }

    public void setBsVatecode(String bsVatecode) {
        this.bsVatecode = bsVatecode;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    @Override
    public String toString() {
        return "UserContractDto{" +
                "userId='" + userId + '\'' +
                ", signName='" + signName + '\'' +
                ", prodId='" + prodId + '\'' +
                ", prodName='" + prodName + '\'' +
                ", bizId='" + bizId + '\'' +
                ", templateType=" + templateType +
                ", model=" + model +
                ", content='" + content + '\'' +
                ", filePath='" + filePath + '\'' +
                ", bsSignid='" + bsSignid + '\'' +
                ", bsDocid='" + bsDocid + '\'' +
                ", bsVatecode='" + bsVatecode + '\'' +
                ", contractName='" + contractName + '\'' +
                ", contractId='" + contractId + '\'' +
                ", signed=" + signed +
                ", signedMark=" + signedMark +
                '}';
    }
}
