package com.xinyunlian.jinfu.yunma.dto;

import com.xinyunlian.jinfu.yunma.enums.EUserRoleType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by menglei on 2017年01月04日.
 */
public class YmUserDetailDto implements Serializable {

    private static final long serialVersionUID = -1L;

    private String name;

    private EUserRoleType type;

    private Integer bankCardCount = 0;

    private Integer storeCount = 0;

    private Integer qrCodeCount = 0;

    private Boolean identityAuth = false;

    private Boolean uploadIdCard = false;//是否上传身份证，false=未传 true=已传

    private Boolean franchise = false;//是否加盟店 false=否 true=是

    private List<String> unAuditQrCodeNoList = new ArrayList<>();//未审核的云码店铺

    private String industryMcc;//行业编号

    private String industryName;//行业名称

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EUserRoleType getType() {
        return type;
    }

    public void setType(EUserRoleType type) {
        this.type = type;
    }

    public Integer getBankCardCount() {
        return bankCardCount;
    }

    public void setBankCardCount(Integer bankCardCount) {
        this.bankCardCount = bankCardCount;
    }

    public Integer getStoreCount() {
        return storeCount;
    }

    public void setStoreCount(Integer storeCount) {
        this.storeCount = storeCount;
    }

    public Integer getQrCodeCount() {
        return qrCodeCount;
    }

    public void setQrCodeCount(Integer qrCodeCount) {
        this.qrCodeCount = qrCodeCount;
    }

    public Boolean getIdentityAuth() {
        return identityAuth;
    }

    public void setIdentityAuth(Boolean identityAuth) {
        this.identityAuth = identityAuth;
    }

    public List<String> getUnAuditQrCodeNoList() {
        return unAuditQrCodeNoList;
    }

    public void setUnAuditQrCodeNoList(List<String> unAuditQrCodeNoList) {
        this.unAuditQrCodeNoList = unAuditQrCodeNoList;
    }

    public Boolean getUploadIdCard() {
        return uploadIdCard;
    }

    public void setUploadIdCard(Boolean uploadIdCard) {
        this.uploadIdCard = uploadIdCard;
    }

    public Boolean getFranchise() {
        return franchise;
    }

    public void setFranchise(Boolean franchise) {
        this.franchise = franchise;
    }

    public String getIndustryMcc() {
        return industryMcc;
    }

    public void setIndustryMcc(String industryMcc) {
        this.industryMcc = industryMcc;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }
}
