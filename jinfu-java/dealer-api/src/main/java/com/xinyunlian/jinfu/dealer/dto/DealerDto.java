package com.xinyunlian.jinfu.dealer.dto;

import com.xinyunlian.jinfu.dealer.enums.EDealerAuditStatus;
import com.xinyunlian.jinfu.dealer.enums.EDealerStatus;
import com.xinyunlian.jinfu.dealer.enums.EDealerType;

import java.io.Serializable;

/**
 * Created by menglei on 2016年08月26日.
 */
public class DealerDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String dealerId;

    private String dealerName;

    private String industryId;

    private Long levelId;

    private String areaId;

    private String provinceId;

    private String cityId;

    private String districtId;

    private String streetId;

    private String province;

    private String city;

    private String area;

    private String street;

    private String address;

    private EDealerType type;

    private String beginTime;

    private String endTime;

    private EDealerAuditStatus auditStatus;

    private String remark;

    private EDealerStatus status;

    private String belongId;

    private String belongName;

    private String belongDuty;

    private DealerExtraDto dealerExtraDto;

    private DealerLevelDto dealerLevelDto;

    private String authAreaCount;//总授权地区

    private String authProdCount;//总授权业务

    private String createTime;

    private String createOpId;

    private String createOpName;

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public EDealerType getType() {
        return type;
    }

    public void setType(EDealerType type) {
        this.type = type;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public EDealerStatus getStatus() {
        return status;
    }

    public void setStatus(EDealerStatus status) {
        this.status = status;
    }

    public DealerExtraDto getDealerExtraDto() {
        return dealerExtraDto;
    }

    public void setDealerExtraDto(DealerExtraDto dealerExtraDto) {
        this.dealerExtraDto = dealerExtraDto;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getStreetId() {
        return streetId;
    }

    public void setStreetId(String streetId) {
        this.streetId = streetId;
    }

    public String getAuthAreaCount() {
        return authAreaCount;
    }

    public void setAuthAreaCount(String authAreaCount) {
        this.authAreaCount = authAreaCount;
    }

    public String getAuthProdCount() {
        return authProdCount;
    }

    public void setAuthProdCount(String authProdCount) {
        this.authProdCount = authProdCount;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public DealerLevelDto getDealerLevelDto() {
        return dealerLevelDto;
    }

    public void setDealerLevelDto(DealerLevelDto dealerLevelDto) {
        this.dealerLevelDto = dealerLevelDto;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateOpName() {
        return createOpName;
    }

    public void setCreateOpName(String createOpName) {
        this.createOpName = createOpName;
    }

    public String getCreateOpId() {
        return createOpId;
    }

    public void setCreateOpId(String createOpId) {
        this.createOpId = createOpId;
    }

    public EDealerAuditStatus getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(EDealerAuditStatus auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBelongId() {
        return belongId;
    }

    public void setBelongId(String belongId) {
        this.belongId = belongId;
    }

    public String getBelongName() {
        return belongName;
    }

    public void setBelongName(String belongName) {
        this.belongName = belongName;
    }

    public String getBelongDuty() {
        return belongDuty;
    }

    public void setBelongDuty(String belongDuty) {
        this.belongDuty = belongDuty;
    }
}
