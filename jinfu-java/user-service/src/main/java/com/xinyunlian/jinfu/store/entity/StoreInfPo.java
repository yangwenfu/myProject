package com.xinyunlian.jinfu.store.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.store.enums.*;
import com.xinyunlian.jinfu.store.enums.converter.*;
import com.xinyunlian.jinfu.user.entity.UserInfoPo;
import com.xinyunlian.jinfu.user.enums.ERelationship;
import com.xinyunlian.jinfu.user.enums.ESource;
import com.xinyunlian.jinfu.user.enums.converter.ERelationshipConverter;
import com.xinyunlian.jinfu.user.enums.converter.ESourceConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by KimLL on 2016/8/18.
 */
@Entity
@Table(name = "STORE_INF")
public class StoreInfPo extends BaseMaintainablePo {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STORE_ID")
    private Long storeId;

    @Column(name = "STORE_NAME")
    private String storeName;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "AREA_ID")
    private String areaId;

    @Column(name = "PROVINCE_ID")
    private String provinceId;

    @Column(name = "CITY_ID")
    private String cityId;

    @Column(name = "DISTRICT_ID")
    private String districtId;

    @Column(name = "STREET_ID")
    private String streetId;

    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "CITY")
    private String city;

    @Column(name = "AREA")
    private String area;

    @Column(name = "STREET")
    private String street;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "FULL_ADDRESS")
    private String fullAddress;

    @Column(name = "LNG")
    private String lng;

    @Column(name = "LAT")
    private String lat;

    @Column(name = "LOCATION_SOURCE")
    @Convert(converter = ELocationSourceConverter.class)
    private ELocationSource locationSource;

    @Column(name = "STATUS")
    @Convert(converter = EStoreStatusConverter.class)
    private EStoreStatus status;

    @Column(name = "TOBACCO_CERTIFICATE_NO")
    private String tobaccoCertificateNo;

    @Column(name = "TOBACCO_END_DATE")
    private Date tobaccoEndDate;

    @Column(name = "BIZ_LICENCE")
    private String bizLicence;

    @Column(name = "BIZ_END_DATE")
    private Date bizEndDate;

    @Column(name = "QR_CODE_URL")
    private String qrCodeUrl;

    @Column(name = "BANK_CARD_NO")
    private String bankCardNo;

    @Column(name = "RELATIONSHIP")
    @Convert(converter = ERelationshipConverter.class)
    private ERelationship relationship;

    @Column(name = "REGISTER_DATE")
    private Date registerDate;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "EMPLOYEE_NUM")
    private Integer employeeNum;

    @Column(name = "BUSINESS_AREA")
    private BigDecimal businessArea;

    @Column(name = "ESTATE")
    @Convert(converter = EEstateConverter.class)
    private EEstate estate;

    @Column(name = "MONTH_SALES")
    private BigDecimal monthSales;

    @Column(name = "MONTH_SALES_APPROVE")
    private BigDecimal monthSalesApprove;

    @Column(name = "MONTH_TOBACCO_BOOK")
    private BigDecimal monthTobaccoBook;

    @Column(name = "BUSINESS_LOCATION")
    @Convert(converter = EBusinessLocationConverter.class)
    private EBusinessLocation businessLocation;

    @Column(name = "TEL")
    private String tel;

    @Column(name = "STORE_TYPE")
    @Convert(converter = EStoreTypeConverter.class)
    private EStoreType storeType;

    @Column(name = "IS_NORMAL")
    private Boolean isNormal;

    @Column(name = "SOURCE")
    @Convert(converter = ESourceConverter.class)
    private ESource source;

    @Column(name = "DEL_REASON")
    @Convert(converter = EDelReasonConverter.class)
    private EDelReason delReason;

    @Column(name = "INDUSTRY_MCC")
    private String industryMcc;

    @Column(name = "LICENCE")
    private String licence;

    @Column(name = "SUID")
    private String suid;

    @Column(name = "UUID")
    private String uuid;

    @Column(name = "SCORE")
    private Integer score;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    private UserInfoPo userInfoPo;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public EStoreStatus getStatus() {
        return status;
    }

    public void setStatus(EStoreStatus status) {
        this.status = status;
    }

    public String getTobaccoCertificateNo() {
        return tobaccoCertificateNo;
    }

    public void setTobaccoCertificateNo(String tobaccoCertificateNo) {
        this.tobaccoCertificateNo = tobaccoCertificateNo;
    }

    public String getBizLicence() {
        return bizLicence;
    }

    public void setBizLicence(String bizLicence) {
        this.bizLicence = bizLicence;
    }

    public Date getTobaccoEndDate() {
        return tobaccoEndDate;
    }

    public void setTobaccoEndDate(Date tobaccoEndDate) {
        this.tobaccoEndDate = tobaccoEndDate;
    }

    public Date getBizEndDate() {
        return bizEndDate;
    }

    public void setBizEndDate(Date bizEndDate) {
        this.bizEndDate = bizEndDate;
    }

    public UserInfoPo getUserInfoPo() {
        return userInfoPo;
    }

    public void setUserInfoPo(UserInfoPo userInfoPo) {
        this.userInfoPo = userInfoPo;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
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

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public ESource getSource() {
        return source;
    }

    public void setSource(ESource source) {
        this.source = source;
    }

    public ERelationship getRelationship() {
        return relationship;
    }

    public void setRelationship(ERelationship relationship) {
        this.relationship = relationship;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(Integer employeeNum) {
        this.employeeNum = employeeNum;
    }

    public BigDecimal getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(BigDecimal businessArea) {
        this.businessArea = businessArea;
    }

    public EEstate getEstate() {
        return estate;
    }

    public void setEstate(EEstate estate) {
        this.estate = estate;
    }

    public EBusinessLocation getBusinessLocation() {
        return businessLocation;
    }

    public void setBusinessLocation(EBusinessLocation businessLocation) {
        this.businessLocation = businessLocation;
    }

    public BigDecimal getMonthSales() {
        return monthSales;
    }

    public void setMonthSales(BigDecimal monthSales) {
        this.monthSales = monthSales;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public BigDecimal getMonthSalesApprove() {
        return monthSalesApprove;
    }

    public void setMonthSalesApprove(BigDecimal monthSalesApprove) {
        this.monthSalesApprove = monthSalesApprove;
    }

    public BigDecimal getMonthTobaccoBook() {
        return monthTobaccoBook;
    }

    public void setMonthTobaccoBook(BigDecimal monthTobaccoBook) {
        this.monthTobaccoBook = monthTobaccoBook;
    }

    public EStoreType getStoreType() {
        return storeType;
    }

    public void setStoreType(EStoreType storeType) {
        this.storeType = storeType;
    }

    public Boolean getIsNormal() {
        return isNormal;
    }

    public void setIsNormal(Boolean isNormal) {
        this.isNormal = isNormal;
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

    public String getSuid() {
        return suid;
    }

    public void setSuid(String suid) {
        this.suid = suid;
    }

    public EDelReason getDelReason() {
        return delReason;
    }

    public void setDelReason(EDelReason delReason) {
        this.delReason = delReason;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public ELocationSource getLocationSource() {
        return locationSource;
    }

    public void setLocationSource(ELocationSource locationSource) {
        this.locationSource = locationSource;
    }
}
