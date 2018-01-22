package com.xinyunlian.jinfu.store.dto.req;

import com.xinyunlian.jinfu.common.dto.PagingDto;
import com.xinyunlian.jinfu.user.enums.ESource;

import java.util.Date;
import java.util.List;

/**
 * Created by KimLL on 2016/9/1.
 */
public class StoreSearchDto extends PagingDto<StoreSearchDto> {
    private static final long serialVersionUID = 1L;

    private Long storeId;

    private String userId;

    private String districtId;

    private String storeName;

    private List<String> districtIds;

    private List<String> areaIds;

    private String province;

    private String city;

    private String area;

    private String street;

    private String areaId;

    private String provinceId;

    private String cityId;

    private String streetId;

    private String fullAddress;

    private String address;

    private String lng;

    private String lat;

    private String tobaccoCertificateNo;

    private String mobile;

    private String userName;

    private String CreateStartDate;

    private String CreateEndDate;

    private List<Long> storeIds;

    private ESource source;

    private Date createTs;

    private String qrCodeUrl;

    private String industryMcc;//行业编号

    private String industryName;//行业名称

    private String licence;//许可证

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

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getTobaccoCertificateNo() {
        return tobaccoCertificateNo;
    }

    public void setTobaccoCertificateNo(String tobaccoCertificateNo) {
        this.tobaccoCertificateNo = tobaccoCertificateNo;
    }


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getDistrictIds() {
        return districtIds;
    }

    public void setDistrictIds(List<String> districtIds) {
        this.districtIds = districtIds;
    }

    public List<Long> getStoreIds() {
        return storeIds;
    }

    public void setStoreIds(List<Long> storeIds) {
        this.storeIds = storeIds;
    }

    public String getCreateStartDate() {
        return CreateStartDate;
    }

    public void setCreateStartDate(String createStartDate) {
        CreateStartDate = createStartDate;
    }

    public String getCreateEndDate() {
        return CreateEndDate;
    }

    public void setCreateEndDate(String createEndDate) {
        CreateEndDate = createEndDate;
    }

    public ESource getSource() {
        return source;
    }

    public void setSource(ESource source) {
        this.source = source;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public List<String> getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(List<String> areaIds) {
        this.areaIds = areaIds;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
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

    public String getIndustryMcc() {
        return industryMcc;
    }

    public void setIndustryMcc(String industryMcc) {
        this.industryMcc = industryMcc;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }
}
