package com.xinyunlian.jinfu.loan.dto.user;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author willwang
 */
public class LoanCUserStoreEachDto implements Serializable {

    private Long storeId;

    @NotNull
    private String storeName;

    private String userId;

    private String provinceId;

    @NotNull
    private String cityId;

    @NotNull
    private String districtId;

    private String streetId;

    @NotNull
    private String areaId;

    @NotEmpty
    private String province;

    @NotEmpty
    private String city;

    @NotEmpty
    private String area;

    private String street;

    /**
     * 实际经营地址
     */
    @NotEmpty
    private String address;

    @NotEmpty
    private String tobaccoCertificateNo;

    @NotEmpty
    private String bizLicence;

    private Date tobaccoEndDate;

    private Date bizEndDate;

    private String qrCodeUrl;

    private String bankCardNo;

    private String source;

    //与证照登记人关系
    @NotEmpty
    private String relationship;

    //店铺物业性质
    @NotEmpty
    private String estate;

    //经营地段
    @NotEmpty
    private String businessLocation;

    //注册成立日期
    @NotNull
    private Date registerDate;

    //实际起始经营日期
    @NotNull
    private Date startDate;

    /**
     * 员工人数
     */
    @DecimalMin("1")
    private Integer employeeNum;

    /**
     * 经营面积
     */
    @DecimalMin("1")
    private BigDecimal businessArea;

    //自述月销售额
    @DecimalMin("1")
    private BigDecimal monthSales;

    //月订烟量
    @DecimalMin("1")
    private BigDecimal monthTobaccoBook;

    /**
     * 联系电话
     */
    @NotEmpty
    private String tel;

    //店铺类型
    private String storeType;

    //经营是否正常
    private Boolean isNormal;
    /**
     * 店铺许可证照片
     */
    @NotEmpty
    private String storeLicencePic;

    /**
     * 店铺许可证照片ID
     */
    private Long storeLicencePicId;

    /**
     * 店铺烟草证号
     */
    @NotEmpty
    private String storeTobaccoPic;

    /**
     * 店铺烟草证号ID
     */
    private Long storeTobaccoPicId;

    /**
     * 店铺门头照
     */
    @NotEmpty
    private String storeHeaderPic;

    /**
     * 店铺门头照ID
     */
    private Long storeHeaderPicId;

    /**
     * 店铺内部照片
     */
    @NotEmpty
    private String storeInnerPic;

    /**
     * 店铺内部照片ID
     */
    private Long storeInnerPicId;

    /**
     * 店铺周边环境照
     */
    @NotEmpty
    private String storeOutsidePic;

    /**
     * 店铺周边环境照ID
     */
    private Long storeOutsidePicId;

    /**
     * 店铺房产证照
     */
    private String storeHouseCertificatePic;

    /**
     * 店铺房产证号ID
     */
    private Long storeHouseCertificatePicId;

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

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getEstate() {
        return estate;
    }

    public void setEstate(String estate) {
        this.estate = estate;
    }

    public String getBusinessLocation() {
        return businessLocation;
    }

    public void setBusinessLocation(String businessLocation) {
        this.businessLocation = businessLocation;
    }

    public BigDecimal getMonthTobaccoBook() {
        return monthTobaccoBook;
    }

    public void setMonthTobaccoBook(BigDecimal monthTobaccoBook) {
        this.monthTobaccoBook = monthTobaccoBook;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public Boolean getIsNormal() {
        return isNormal;
    }

    public void setIsNormal(Boolean isNormal) {
        this.isNormal = isNormal;
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
