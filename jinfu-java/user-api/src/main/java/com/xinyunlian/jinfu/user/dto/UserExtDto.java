package com.xinyunlian.jinfu.user.dto;

import com.xinyunlian.jinfu.user.enums.EHouseProperty;
import com.xinyunlian.jinfu.user.enums.EMarryStatus;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * 用户扩展Entity
 *
 * @author KimLL
 */

public class UserExtDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userName = "";

    private String userId = "";

    private String idCardNo = "";

    private String province = "";

    private Long areaId;

    private Long provinceId;

    private Long cityId;

    private Long streetId;

    private String city = "";

    private String area = "";

    private String street = "";

    private String address = "";

    private String phone = "";

    private EMarryStatus marryStatus;

    //本地是否有房产
    private Boolean hasHouse = false;

    //外地是否有房产
    private Boolean hasOutHouse = false;

    //是否活体
    private Boolean lived;

    //订烟系统是否授权
    private Boolean tobaccoAuth = false;

    //依图-比对是否通过
    private Boolean yituPass;

    //依图-比对相似度结果
    private Double yituSimilarity;

    //是否实名
    private Boolean identityAuth;

    //住宅性质
    private EHouseProperty houseProperty;

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
     * 户口本户主页照片
     */
    private String householderBookletPic;

    /**
     * 户口本户主页照片Id
     */
    private Long householderBookletPicId = 0L;

    /**
     * 户口本照片
     */
    private String residenceBookletPic = "";

    /**
     * 户口本照片Id
     */
    private Long residenceBookletPicId = 0L;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return StringUtils.isNotEmpty(userName) ? userName : "";
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCardNo() {
        return StringUtils.isNotEmpty(idCardNo) ? idCardNo : "";
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
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

    public EMarryStatus getMarryStatus() {
        return marryStatus;
    }

    public void setMarryStatus(EMarryStatus marryStatus) {
        this.marryStatus = marryStatus;
    }

    public Boolean getHasHouse() {
        return hasHouse;
    }

    public void setHasHouse(Boolean hasHouse) {
        this.hasHouse = hasHouse;
    }

    public Boolean getHasOutHouse() {
        return hasOutHouse;
    }

    public void setHasOutHouse(Boolean hasOutHouse) {
        this.hasOutHouse = hasOutHouse;
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

    public String getIdCardBackPic() {
        return idCardBackPic;
    }

    public void setIdCardBackPic(String idCardBackPic) {
        this.idCardBackPic = idCardBackPic;
    }

    public String getHeldIdCardPic() {
        return heldIdCardPic;
    }

    public void setHeldIdCardPic(String heldIdCardPic) {
        this.heldIdCardPic = heldIdCardPic;
    }

    public String getHouseCertificatePic() {
        return houseCertificatePic;
    }

    public void setHouseCertificatePic(String houseCertificatePic) {
        this.houseCertificatePic = houseCertificatePic;
    }

    public String getMarryCertificatePic() {
        return marryCertificatePic;
    }

    public void setMarryCertificatePic(String marryCertificatePic) {
        this.marryCertificatePic = marryCertificatePic;
    }

    public String getResidenceBookletPic() {
        return residenceBookletPic;
    }

    public void setResidenceBookletPic(String residenceBookletPic) {
        this.residenceBookletPic = residenceBookletPic;
    }

    public Long getIdCardFrontPicId() {
        return idCardFrontPicId;
    }

    public void setIdCardFrontPicId(Long idCardFrontPicId) {
        this.idCardFrontPicId = idCardFrontPicId;
    }

    public Long getIdCardBackPicId() {
        return idCardBackPicId;
    }

    public void setIdCardBackPicId(Long idCardBackPicId) {
        this.idCardBackPicId = idCardBackPicId;
    }

    public Long getHeldIdCardPicId() {
        return heldIdCardPicId;
    }

    public void setHeldIdCardPicId(Long heldIdCardPicId) {
        this.heldIdCardPicId = heldIdCardPicId;
    }

    public Long getHouseCertificatePicId() {
        return houseCertificatePicId;
    }

    public void setHouseCertificatePicId(Long houseCertificatePicId) {
        this.houseCertificatePicId = houseCertificatePicId;
    }

    public Long getMarryCertificatePicId() {
        return marryCertificatePicId;
    }

    public void setMarryCertificatePicId(Long marryCertificatePicId) {
        this.marryCertificatePicId = marryCertificatePicId;
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

    public Long getResidenceBookletPicId() {
        return residenceBookletPicId;
    }

    public void setResidenceBookletPicId(Long residenceBookletPicId) {
        this.residenceBookletPicId = residenceBookletPicId;
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

    public Boolean getLived() {
        return lived;
    }

    public void setLived(Boolean lived) {
        this.lived = lived;
    }

    public Boolean getIdentityAuth() {
        return identityAuth;
    }

    public void setIdentityAuth(Boolean identityAuth) {
        this.identityAuth = identityAuth;
    }

    public Boolean getTobaccoAuth() {
        return tobaccoAuth;
    }

    public void setTobaccoAuth(Boolean tobaccoAuth) {
        this.tobaccoAuth = tobaccoAuth;
    }
}


