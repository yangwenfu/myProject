package com.xinyunlian.jinfu.user.dto.ext;

import com.xinyunlian.jinfu.user.enums.EHouseProperty;

import java.util.Date;

/**
 * Created by King on 2017/2/17.
 */
public class UserExtBaseDto extends UserExtIdDto {
    private static final long serialVersionUID = 5595933902366740800L;

    private String userName = "";

    private String idCardNo = "";

    private String mobile;

    //身份证有效期起始时间
    private Date idExpiredBegin;
    //身份证有效期结束时间
    private Date idExpiredEnd;
    //身份证发证机关
    private String idAuthority;

    //依图-比对是否通过
    private Boolean yituPass;

    //依图-比对相似度结果
    private Double yituSimilarity;

    private String province = "";

    private String city = "";

    private String area = "";

    private String street = "";

    private String address = "";

    private String phone = "";

    private Long areaId;

    private Long provinceId;

    private Long cityId;

    private Long streetId;

    private Long nationProvinceId;

    private Long nationCityId;

    private Long nationAreaId;

    //实名时间
    private Date identityAuthDate;

    //住宅性质
    private EHouseProperty houseProperty;

    /**
     * 身份证正面
     */
    private String idCardFrontPic = "";

    /**
     * 身份证正面
     */
    private Long idCardFrontPicId = 0L;

    /**
     * 身份证背面
     */
    private String idCardBackPic = "";

    /**
     * 身份证背面Id
     */
    private Long idCardBackPicId = 0L;

    /**
     * 手持身份证
     */
    private String heldIdCardPic = "";

    /**
     * 手持身份证Id
     */
    private Long heldIdCardPicId = 0L;

    /**
     * 房产证照片
     */
    private String houseCertificatePic = "";

    /**
     * 房产证照片Id
     */
    private Long houseCertificatePicId = 0L;

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

    /**
     * 户口本户主页照片
     */
    private String householderBookletPic;

    /**
     * 户口本户主页照片Id
     */
    private Long householderBookletPicId = 0L;

    //出身日期
    private Date birthDate;
    //性别
    private String sex;
    //学历状况
    private String education;
    //户籍省
    private String nationProvince;
    //户籍市
    private String nationCity;
    //户籍区
    private String nationArea;
    //民族
    private String nation;
    //户籍住址
    private String nationAddress;
    //现地址居住时间(年)
    private Integer liveAddressYears;
    //籍与居住地一致
    private Boolean addressAsNation;
    //来本市时间
    private Date liveHereDate;
    //有无驾照
    private Boolean hasDl;
    //驾照类型
    private String dlType;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getIdExpiredBegin() {
        return idExpiredBegin;
    }

    public void setIdExpiredBegin(Date idExpiredBegin) {
        this.idExpiredBegin = idExpiredBegin;
    }

    public Date getIdExpiredEnd() {
        return idExpiredEnd;
    }

    public void setIdExpiredEnd(Date idExpiredEnd) {
        this.idExpiredEnd = idExpiredEnd;
    }

    public String getIdAuthority() {
        return idAuthority;
    }

    public void setIdAuthority(String idAuthority) {
        this.idAuthority = idAuthority;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
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

    public Long getStreetId() {
        return streetId;
    }

    public void setStreetId(Long streetId) {
        this.streetId = streetId;
    }

    public Long getNationProvinceId() {
        return nationProvinceId;
    }

    public void setNationProvinceId(Long nationProvinceId) {
        this.nationProvinceId = nationProvinceId;
    }

    public Long getNationCityId() {
        return nationCityId;
    }

    public void setNationCityId(Long nationCityId) {
        this.nationCityId = nationCityId;
    }

    public Long getNationAreaId() {
        return nationAreaId;
    }

    public void setNationAreaId(Long nationAreaId) {
        this.nationAreaId = nationAreaId;
    }

    public String getNationAddress() {
        return nationAddress;
    }

    public void setNationAddress(String nationAddress) {
        this.nationAddress = nationAddress;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getIdentityAuthDate() {
        return identityAuthDate;
    }

    public void setIdentityAuthDate(Date identityAuthDate) {
        this.identityAuthDate = identityAuthDate;
    }

    public EHouseProperty getHouseProperty() {
        return houseProperty;
    }

    public void setHouseProperty(EHouseProperty houseProperty) {
        this.houseProperty = houseProperty;
    }

    public String getIdCardFrontPic() {
        return idCardFrontPic;
    }

    public void setIdCardFrontPic(String idCardFrontPic) {
        this.idCardFrontPic = idCardFrontPic;
    }

    public Long getIdCardFrontPicId() {
        return idCardFrontPicId;
    }

    public void setIdCardFrontPicId(Long idCardFrontPicId) {
        this.idCardFrontPicId = idCardFrontPicId;
    }

    public String getIdCardBackPic() {
        return idCardBackPic;
    }

    public void setIdCardBackPic(String idCardBackPic) {
        this.idCardBackPic = idCardBackPic;
    }

    public Long getIdCardBackPicId() {
        return idCardBackPicId;
    }

    public void setIdCardBackPicId(Long idCardBackPicId) {
        this.idCardBackPicId = idCardBackPicId;
    }

    public String getHeldIdCardPic() {
        return heldIdCardPic;
    }

    public void setHeldIdCardPic(String heldIdCardPic) {
        this.heldIdCardPic = heldIdCardPic;
    }

    public Long getHeldIdCardPicId() {
        return heldIdCardPicId;
    }

    public void setHeldIdCardPicId(Long heldIdCardPicId) {
        this.heldIdCardPicId = heldIdCardPicId;
    }

    public String getHouseCertificatePic() {
        return houseCertificatePic;
    }

    public void setHouseCertificatePic(String houseCertificatePic) {
        this.houseCertificatePic = houseCertificatePic;
    }

    public Long getHouseCertificatePicId() {
        return houseCertificatePicId;
    }

    public void setHouseCertificatePicId(Long houseCertificatePicId) {
        this.houseCertificatePicId = houseCertificatePicId;
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

    public Boolean getYituPass() {
        return yituPass;
    }

    public void setYituPass(Boolean yituPass) {
        this.yituPass = yituPass;
    }

    public Double getYituSimilarity() {
        return yituSimilarity;
    }

    public void setYituSimilarity(Double yituSimilarity) {
        this.yituSimilarity = yituSimilarity;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getNationProvince() {
        return nationProvince;
    }

    public void setNationProvince(String nationProvince) {
        this.nationProvince = nationProvince;
    }

    public String getNationCity() {
        return nationCity;
    }

    public void setNationCity(String nationCity) {
        this.nationCity = nationCity;
    }

    public String getNationArea() {
        return nationArea;
    }

    public void setNationArea(String nationArea) {
        this.nationArea = nationArea;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public Integer getLiveAddressYears() {
        return liveAddressYears;
    }

    public void setLiveAddressYears(Integer liveAddressYears) {
        this.liveAddressYears = liveAddressYears;
    }

    public Boolean getAddressAsNation() {
        return addressAsNation;
    }

    public void setAddressAsNation(Boolean addressAsNation) {
        this.addressAsNation = addressAsNation;
    }

    public Date getLiveHereDate() {
        return liveHereDate;
    }

    public void setLiveHereDate(Date liveHereDate) {
        this.liveHereDate = liveHereDate;
    }

    public Boolean getHasDl() {
        return hasDl;
    }

    public void setHasDl(Boolean hasDl) {
        this.hasDl = hasDl;
    }

    public String getHouseholderBookletPic() {
        return householderBookletPic;
    }

    public void setHouseholderBookletPic(String householderBookletPic) {
        this.householderBookletPic = householderBookletPic;
    }

    public Long getHouseholderBookletPicId() {
        return householderBookletPicId;
    }

    public void setHouseholderBookletPicId(Long householderBookletPicId) {
        this.householderBookletPicId = householderBookletPicId;
    }

    public String getDlType() {
        return dlType;
    }

    public void setDlType(String dlType) {
        this.dlType = dlType;
    }
}
