package com.xinyunlian.jinfu.contract.entity;
import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;
import com.xinyunlian.jinfu.contract.enums.ESignedMark;
import com.xinyunlian.jinfu.contract.enums.converter.ECntrctTmpltTypeConverter;
import com.xinyunlian.jinfu.contract.enums.converter.ESignedMarkConverter;

import javax.persistence.*;
import java.util.Date;
/**
 * Created by JL on 2016/9/20.
 */
@Entity
@Table(name = "USER_CONTRACT")
public class UserContractPo extends BaseMaintainablePo {
    @Id
    @Column(name = "CNTRCT_ID")
    private String contractId;
    @Column(name = "CNTRCT_NAME")
    private String contractName;
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "SIGN_NAME")
    private String signName;
    @Column(name = "SIGN_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date signDate;
    @Column(name = "PROD_ID")
    private String prodId;
    @Column(name = "PROD_NAME")
    private String prodName;
    @Column(name = "BIZ_ID")
    private String bizId;
    @Column(name = "TEMPLATE_TYPE")
    @Convert(converter = ECntrctTmpltTypeConverter.class)
    private ECntrctTmpltType templateType;

    @Column(name = "signed_mark")
    @Convert(converter = ESignedMarkConverter.class)
    private ESignedMark signedMark;

    @Column(name = "CONTENT")
    private String content;
    @Column(name = "FILE_PATH")
    private String filePath;
    @Column(name = "BS_SIGNID")
    private String bsSignid;
    @Column(name = "BS_DOCID")
    private String bsDocid;
    @Column(name = "BS_VATECODE")
    private String bsVatecode;
    public String getContractId() {
        return contractId;
    }
    public void setContractId(String contractId) {
        this.contractId = contractId;
    }
    public String getContractName() {
        return contractName;
    }
    public void setContractName(String contractName) {
        this.contractName = contractName;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getSignName() {
        return signName;
    }
    public void setSignName(String signName) {
        this.signName = signName;
    }
    public Date getSignDate() {
        return signDate;
    }
    public void setSignDate(Date signDate) {
        this.signDate = signDate;
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


    public ESignedMark getSignedMark() {
        return signedMark;
    }

    public void setSignedMark(ESignedMark signedMark) {
        this.signedMark = signedMark;
    }
}