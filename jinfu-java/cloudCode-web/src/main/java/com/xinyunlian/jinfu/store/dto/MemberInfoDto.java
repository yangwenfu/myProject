package com.xinyunlian.jinfu.store.dto;

import com.xinyunlian.jinfu.yunma.enums.EMemberIntoStatus;
import com.xinyunlian.jinfu.yunma.enums.ESettlement;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 云码商铺Entity
 *
 * @author menglei
 */

public class MemberInfoDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long storeId;
    private String userId;
    private String storeName;
    private String memberNo;
    private String qrcodeNo;
    private String qrcodeUrl;
    private Date bindTime;
    private Long bankCardId;
    private String bankCardNo;
    private String bankName;
    private ESettlement settlement;
    private Boolean activate;//用户云码激活状态
    private Boolean selected = false;//合作方选中店铺
    private String province = "";
    private String city = "";
    private String area = "";
    private String street = "";
    private EMemberIntoStatus memberIntoStatus;//进件状态
    private Boolean applyed = true;//实体码是否已申请 true=已申请 false=未申请

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getQrcodeNo() {
        return qrcodeNo;
    }

    public void setQrcodeNo(String qrcodeNo) {
        this.qrcodeNo = qrcodeNo;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public Date getBindTime() {
        return bindTime;
    }

    public void setBindTime(Date bindTime) {
        this.bindTime = bindTime;
    }

    public Long getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(Long bankCardId) {
        this.bankCardId = bankCardId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getBankCardNo() {
        if (StringUtils.isEmpty(bankCardNo)) {
            return bankCardNo;
        }
        return "**** **** **** " + bankCardNo.substring(bankCardNo.length() - 4, bankCardNo.length());
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public ESettlement getSettlement() {
        return settlement;
    }

    public void setSettlement(ESettlement settlement) {
        this.settlement = settlement;
    }

    public Boolean getActivate() {
        return activate;
    }

    public void setActivate(Boolean activate) {
        this.activate = activate;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public EMemberIntoStatus getMemberIntoStatus() {
        return memberIntoStatus;
    }

    public void setMemberIntoStatus(EMemberIntoStatus memberIntoStatus) {
        this.memberIntoStatus = memberIntoStatus;
    }

    public Boolean getApplyed() {
        return applyed;
    }

    public void setApplyed(Boolean applyed) {
        this.applyed = applyed;
    }
}


