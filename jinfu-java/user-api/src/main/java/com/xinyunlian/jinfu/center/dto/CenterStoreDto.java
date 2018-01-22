package com.xinyunlian.jinfu.center.dto;

import java.io.Serializable;

/**
 * Created by King on 2017/5/10.
 */
public class CenterStoreDto implements Serializable{
    private static final long serialVersionUID = 2259715942101440482L;
    private Long storeId;
    //店铺唯一号
    private String uuid;
    //用户UUID
    private String userUUID;
    //店铺名称
    private String storeName;
    //中文省
    private String province;
    //中文市
    private String city;
    //中文区/县
    private String area;
    //中文街道/村
    private String street;
    //店铺地址（店铺所在地）
    private String storeAddress;
    //店铺电话
    private String storePhone;
    //地区国标
    private String areaGbCode;
    //工商号
    private String businessLicence;
    //营业执照开始时间
    private String businessStartDate;
    //营业执照结束时间
    private String businessEndDate;
    //烟草号
    private String tobaccoLicence;
    //烟草开始时间
    private String tobaccoStartDate;
    // 烟草结束时间
    private String tobaccoEndDate;
    //经度
    private Double lng;
    //纬度
    private Double lat;
    //店铺状态
    private Integer status;
    //店铺别名
    private String aliasName;
    //主营类目
    private String mainBus;
    //店铺类型
    private Integer storeType;
    //入住时间
    private String enterTime;
    //经营面积
    private Double businessArea;
    //店铺物业性质
    private Integer estate;
    //用户与店铺许可证照关系
    private String relationship;
    //数据来源
    private String source = "金服";
    //员工人数
    private Integer employeeNum;
    //地区ID

    private Long provinceId;

    private Long cityId;

    private Long areaId;

    private Long streetId;

    private String streetGbCode;
    //操作者id
    private String operatorId;
    //操作者name
    private String operatorName;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUserUUID() {
        return userUUID;
    }

    public void setUserUUID(String userUUID) {
        this.userUUID = userUUID;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }

    public String getAreaGbCode() {
        return areaGbCode;
    }

    public void setAreaGbCode(String areaGbCode) {
        this.areaGbCode = areaGbCode;
    }

    public String getBusinessLicence() {
        return businessLicence;
    }

    public void setBusinessLicence(String businessLicence) {
        this.businessLicence = businessLicence;
    }

    public String getBusinessStartDate() {
        return businessStartDate;
    }

    public void setBusinessStartDate(String businessStartDate) {
        this.businessStartDate = businessStartDate;
    }

    public String getBusinessEndDate() {
        return businessEndDate;
    }

    public void setBusinessEndDate(String businessEndDate) {
        this.businessEndDate = businessEndDate;
    }

    public String getTobaccoLicence() {
        return tobaccoLicence;
    }

    public void setTobaccoLicence(String tobaccoLicence) {
        this.tobaccoLicence = tobaccoLicence;
    }

    public String getTobaccoStartDate() {
        return tobaccoStartDate;
    }

    public void setTobaccoStartDate(String tobaccoStartDate) {
        this.tobaccoStartDate = tobaccoStartDate;
    }

    public String getTobaccoEndDate() {
        return tobaccoEndDate;
    }

    public void setTobaccoEndDate(String tobaccoEndDate) {
        this.tobaccoEndDate = tobaccoEndDate;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getMainBus() {
        return mainBus;
    }

    public void setMainBus(String mainBus) {
        this.mainBus = mainBus;
    }

    public Integer getStoreType() {
        return storeType;
    }

    public void setStoreType(Integer storeType) {
        this.storeType = storeType;
    }

    public String getEnterTime() {
        return enterTime;
    }

    public void setEnterTime(String enterTime) {
        this.enterTime = enterTime;
    }

    public Double getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(Double businessArea) {
        this.businessArea = businessArea;
    }

    public Integer getEstate() {
        return estate;
    }

    public void setEstate(Integer estate) {
        this.estate = estate;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(Integer employeeNum) {
        this.employeeNum = employeeNum;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Long getStreetId() {
        return streetId;
    }

    public void setStreetId(Long streetId) {
        this.streetId = streetId;
    }

    public String getStreetGbCode() {
        return streetGbCode;
    }

    public void setStreetGbCode(String streetGbCode) {
        this.streetGbCode = streetGbCode;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }
}
