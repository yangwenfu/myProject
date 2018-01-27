package com.xinyunlian.jinfu.dealer.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;

import java.util.List;

/**
 * Created by menglei on 2016年08月29日.
 */
public class DealerSearchDto extends PagingDto<DealerDto> {

    private static final long serialVersionUID = 1L;

    private String dealerName;

    private String industryId;

    private Long levelId;

    private String provinceId;

    private String cityId;

    private String areaId;

    private String streetId;

    private String province;

    private String city;

    private String area;

    private String street;

    private String prodId;

    private String beginTime;

    private String endTime;

    private String auditStatus;

    private String type;

    private String prodProvinceId;

    private String prodCityId;

    private String prodAreaId;

    private String prodStreetId;

    private String duty;

    private String status;

    private String belongName;

    private String createOpName;

    private List<String> cityIds;

    private List<String> provinceIds;

    private List<String> createOpIds;

    private List<String> belongIds;

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

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
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

    public String getStreetId() {
        return streetId;
    }

    public void setStreetId(String streetId) {
        this.streetId = streetId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProdProvinceId() {
        return prodProvinceId;
    }

    public void setProdProvinceId(String prodProvinceId) {
        this.prodProvinceId = prodProvinceId;
    }

    public String getProdCityId() {
        return prodCityId;
    }

    public void setProdCityId(String prodCityId) {
        this.prodCityId = prodCityId;
    }

    public String getProdAreaId() {
        return prodAreaId;
    }

    public void setProdAreaId(String prodAreaId) {
        this.prodAreaId = prodAreaId;
    }

    public String getProdStreetId() {
        return prodStreetId;
    }

    public void setProdStreetId(String prodStreetId) {
        this.prodStreetId = prodStreetId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public List<String> getCityIds() {
        return cityIds;
    }

    public void setCityIds(List<String> cityIds) {
        this.cityIds = cityIds;
    }

    public List<String> getProvinceIds() {
        return provinceIds;
    }

    public void setProvinceIds(List<String> provinceIds) {
        this.provinceIds = provinceIds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBelongName() {
        return belongName;
    }

    public void setBelongName(String belongName) {
        this.belongName = belongName;
    }

    public String getCreateOpName() {
        return createOpName;
    }

    public void setCreateOpName(String createOpName) {
        this.createOpName = createOpName;
    }

    public List<String> getCreateOpIds() {
        return createOpIds;
    }

    public void setCreateOpIds(List<String> createOpIds) {
        this.createOpIds = createOpIds;
    }

    public List<String> getBelongIds() {
        return belongIds;
    }

    public void setBelongIds(List<String> belongIds) {
        this.belongIds = belongIds;
    }
}
