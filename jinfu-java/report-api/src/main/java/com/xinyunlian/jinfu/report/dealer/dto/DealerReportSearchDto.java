package com.xinyunlian.jinfu.report.dealer.dto;

import java.io.Serializable;

/**
 * Created by bright on 2016/11/29.
 */
public class DealerReportSearchDto implements Serializable {
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

    private String type;

    private String prodProvinceId;

    private String prodCityId;

    private String prodAreaId;

    private String prodStreetId;

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

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getStreetId() {
        return streetId;
    }

    public void setStreetId(String streetId) {
        this.streetId = streetId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
