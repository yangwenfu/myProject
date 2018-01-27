package com.xinyunlian.jinfu.insurance.dto;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.xinyunlian.jinfu.store.enums.EBusinessLocation;
import com.xinyunlian.jinfu.store.enums.EEstate;
import com.xinyunlian.jinfu.store.enums.EStoreType;
import com.xinyunlian.jinfu.user.enums.ERelationship;
import com.xinyunlian.jinfu.user.enums.ESource;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by KimLL on 2016/8/18.
 */
public class StoreDto implements Serializable{
    private static final long serialVersionUID = 1L;
    private Long storeId;

    @NotEmpty
    private String storeName = "";

    private String userId = "";

    private String provinceId = "";

    private String cityId = "";

    private String districtId = "";

    private String streetId = "";

    private String areaId = "";

    private String province = "";

    private String city = "";

    private String area = "";

    @NotEmpty
    private String street = "";

    @NotEmpty
    private String address = "";

    private String tobaccoCertificateNo = "";

    @NotEmpty
    private String bizLicence = "";

    private String industryMcc = "5227";

    @NotEmpty
    private String licence;

    private Date tobaccoEndDate;

    private Date bizEndDate;

    private String qrCodeUrl = "";

    private String bankCardNo = "";

    private ESource source;

    //与证照登记人关系
    private ERelationship relationship;

    //注册成立日期
    private Date registerDate;

    //实际起始经营日期
    private Date startDate;

    //员工人数
    private Integer employeeNum = 0;

    //经营面积
    private BigDecimal businessArea = BigDecimal.ZERO;

    //店铺物业性质
    private EEstate estate;

    //自述月销售额
    private BigDecimal monthSales = BigDecimal.ZERO;

    //月订烟量
    private BigDecimal monthTobaccoBook = BigDecimal.ZERO;

    //经营地段

    private EBusinessLocation businessLocation;

    //电话
    private String tel = "";

    //店铺类型
    private EStoreType storeType;

    //经营是否正常
    private Boolean isNormal = false;
    /**
     * 店铺许可证照片
     */
    private String storeLicencePic = "";

    /**
     * 店铺许可证照片ID
     */
    private Long storeLicencePicId = 0L;

    /**
     * 店铺烟草证号
     */
    private String storeTobaccoPic = "";

    /**
     * 店铺烟草证号ID
     */
    private Long storeTobaccoPicId = 0L;

    /**
     * 店铺门头照
     */
    private String storeHeaderPic = "";

    /**
     * 店铺门头照ID
     */
    private Long storeHeaderPicId = 0L;

    /**
     * 店铺内部照片
     */
    private String storeInnerPic = "";

    /**
     * 店铺内部照片ID
     */
    private Long storeInnerPicId = 0L;

    /**
     * 店铺周边环境照
     */
    private String storeOutsidePic = "";

    /**
     * 店铺周边环境照ID
     */
    private Long storeOutsidePicId = 0L;

    /**
     * 店铺房产证照
     */
    private String storeHouseCertificatePic = "";

    /**
     * 店铺房产证号ID
     */
    private Long storeHouseCertificatePicId = 0L;

    /**
     * 结婚证照片
     */
    private String marryCertificatePic = "";

    /**
     * 结婚证照片Id
     */
    private Long marryCertificatePicId = 0L;

    /**
     * 户口本照片
     */
    private String residenceBookletPic = "";

    /**
     * 户口本照片Id
     */
    private Long residenceBookletPicId = 0L;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return StringUtils.isNotEmpty(storeName) ? storeName : "";
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getUserId() {
        return StringUtils.isNotEmpty(userId) ? userId : "";
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAreaId() {
        return StringUtils.isNotEmpty(areaId) ? areaId : "";
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getProvince() {
        return StringUtils.isNotEmpty(province) ? province : "";
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return StringUtils.isNotEmpty(city) ? city : "";
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return StringUtils.isNotEmpty(area) ? area : "";
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreet() {
        return StringUtils.isNotEmpty(street) ? street : "";
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return StringUtils.isNotEmpty(address) ? address : "";
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTobaccoCertificateNo() {
        return StringUtils.isNotEmpty(tobaccoCertificateNo) ? tobaccoCertificateNo : "";
    }

    public void setTobaccoCertificateNo(String tobaccoCertificateNo) {
        this.tobaccoCertificateNo = tobaccoCertificateNo;
    }

    public String getBizLicence() {
        return StringUtils.isNotEmpty(bizLicence) ? bizLicence : "";
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

    public String getQrCodeUrl() {
        return StringUtils.isNotEmpty(qrCodeUrl) ? qrCodeUrl : "";
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
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
        return StringUtils.isNotEmpty(bankCardNo) ? bankCardNo : "";
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

    public String getStoreLicencePic() {
        return storeLicencePic;
    }

    public void setStoreLicencePic(String storeLicencePic) {
        this.storeLicencePic = storeLicencePic;
    }

    public Long getStoreLicencePicId() {
        return storeLicencePicId;
    }

    public void setStoreLicencePicId(Long storeLicencePicId) {
        this.storeLicencePicId = storeLicencePicId;
    }

    public String getStoreTobaccoPic() {
        return storeTobaccoPic;
    }

    public void setStoreTobaccoPic(String storeTobaccoPic) {
        this.storeTobaccoPic = storeTobaccoPic;
    }

    public Long getStoreTobaccoPicId() {
        return storeTobaccoPicId;
    }

    public void setStoreTobaccoPicId(Long storeTobaccoPicId) {
        this.storeTobaccoPicId = storeTobaccoPicId;
    }

    public String getStoreHeaderPic() {
        return storeHeaderPic;
    }

    public void setStoreHeaderPic(String storeHeaderPic) {
        this.storeHeaderPic = storeHeaderPic;
    }

    public Long getStoreHeaderPicId() {
        return storeHeaderPicId;
    }

    public void setStoreHeaderPicId(Long storeHeaderPicId) {
        this.storeHeaderPicId = storeHeaderPicId;
    }

    public String getStoreInnerPic() {
        return storeInnerPic;
    }

    public void setStoreInnerPic(String storeInnerPic) {
        this.storeInnerPic = storeInnerPic;
    }

    public Long getStoreInnerPicId() {
        return storeInnerPicId;
    }

    public void setStoreInnerPicId(Long storeInnerPicId) {
        this.storeInnerPicId = storeInnerPicId;
    }

    public String getStoreOutsidePic() {
        return storeOutsidePic;
    }

    public void setStoreOutsidePic(String storeOutsidePic) {
        this.storeOutsidePic = storeOutsidePic;
    }

    public Long getStoreOutsidePicId() {
        return storeOutsidePicId;
    }

    public void setStoreOutsidePicId(Long storeOutsidePicId) {
        this.storeOutsidePicId = storeOutsidePicId;
    }

    public String getStoreHouseCertificatePic() {
        return storeHouseCertificatePic;
    }

    public void setStoreHouseCertificatePic(String storeHouseCertificatePic) {
        this.storeHouseCertificatePic = storeHouseCertificatePic;
    }

    public Long getStoreHouseCertificatePicId() {
        return storeHouseCertificatePicId;
    }

    public void setStoreHouseCertificatePicId(Long storeHouseCertificatePicId) {
        this.storeHouseCertificatePicId = storeHouseCertificatePicId;
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
        return employeeNum == null ? 0 : employeeNum;
    }

    public void setEmployeeNum(Integer employeeNum) {
        this.employeeNum = employeeNum;
    }

    public BigDecimal getBusinessArea() {
        return businessArea == null ? BigDecimal.ZERO : businessArea;
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

    public BigDecimal getMonthSales() {
        return monthSales == null ? BigDecimal.ZERO : monthSales;
    }

    public void setMonthSales(BigDecimal monthSales) {
        this.monthSales = monthSales;
    }

    public EBusinessLocation getBusinessLocation() {
        return businessLocation;
    }

    public void setBusinessLocation(EBusinessLocation businessLocation) {
        this.businessLocation = businessLocation;
    }

    public String getTel() {
        return StringUtils.isNotEmpty(tel) ? tel : "";
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public BigDecimal getMonthTobaccoBook() {
        return monthTobaccoBook == null ? BigDecimal.ZERO : monthTobaccoBook;
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

    @Override
    public String toString() {
        return "StoreInfDto{" +
                "storeId=" + storeId +
                ", storeName='" + storeName + '\'' +
                ", userId='" + userId + '\'' +
                ", provinceId='" + provinceId + '\'' +
                ", cityId='" + cityId + '\'' +
                ", districtId='" + districtId + '\'' +
                ", streetId='" + streetId + '\'' +
                ", areaId='" + areaId + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", street='" + street + '\'' +
                ", address='" + address + '\'' +
                ", tobaccoCertificateNo='" + tobaccoCertificateNo + '\'' +
                ", bizLicence='" + bizLicence + '\'' +
                ", tobaccoEndDate=" + tobaccoEndDate +
                ", bizEndDate=" + bizEndDate +
                ", qrCodeUrl='" + qrCodeUrl + '\'' +
                ", bankCardNo='" + bankCardNo + '\'' +
                ", source=" + source +
                ", relationship=" + relationship +
                ", registerDate=" + registerDate +
                ", startDate=" + startDate +
                ", employeeNum=" + employeeNum +
                ", businessArea=" + businessArea +
                ", estate=" + estate +
                ", monthSales=" + monthSales +
                ", monthTobaccoBook=" + monthTobaccoBook +
                ", businessLocation=" + businessLocation +
                ", tel='" + tel + '\'' +
                ", storeType=" + storeType +
                ", isNormal=" + isNormal +
                ", storeLicencePic='" + storeLicencePic + '\'' +
                ", storeLicencePicId=" + storeLicencePicId +
                ", storeTobaccoPic='" + storeTobaccoPic + '\'' +
                ", storeTobaccoPicId=" + storeTobaccoPicId +
                ", storeHeaderPic='" + storeHeaderPic + '\'' +
                ", storeHeaderPicId=" + storeHeaderPicId +
                ", storeInnerPic='" + storeInnerPic + '\'' +
                ", storeInnerPicId=" + storeInnerPicId +
                ", storeOutsidePic='" + storeOutsidePic + '\'' +
                ", storeOutsidePicId=" + storeOutsidePicId +
                ", storeHouseCertificatePic='" + storeHouseCertificatePic + '\'' +
                ", storeHouseCertificatePicId=" + storeHouseCertificatePicId +
                '}';
    }

    public Boolean getNormal() {
        return isNormal;
    }

    public void setNormal(Boolean normal) {
        isNormal = normal;
    }

    public String getMarryCertificatePic() {
        return marryCertificatePic;
    }

    public void setMarryCertificatePic(String marryCertificatePic) {
        this.marryCertificatePic = marryCertificatePic;
    }

    public Long getMarryCertificatePicId() {
        return marryCertificatePicId;
    }

    public void setMarryCertificatePicId(Long marryCertificatePicId) {
        this.marryCertificatePicId = marryCertificatePicId;
    }

    public String getResidenceBookletPic() {
        return residenceBookletPic;
    }

    public void setResidenceBookletPic(String residenceBookletPic) {
        this.residenceBookletPic = residenceBookletPic;
    }

    public Long getResidenceBookletPicId() {
        return residenceBookletPicId;
    }

    public void setResidenceBookletPicId(Long residenceBookletPicId) {
        this.residenceBookletPicId = residenceBookletPicId;
    }
}
