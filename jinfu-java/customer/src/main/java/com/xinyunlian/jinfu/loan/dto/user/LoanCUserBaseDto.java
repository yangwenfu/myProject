package com.xinyunlian.jinfu.loan.dto.user;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author willwang
 */
public class LoanCUserBaseDto implements Serializable{

    //用户名字
    @NotBlank
    private String userName;

    //身份证号
    @NotEmpty
    private String idCardNo;

    //婚姻状态
    @NotEmpty
    private String marryStatus;

    //手机
    private String mobile;

    //联系电话
    @NotEmpty
    private String phone;

    //住宅性质
    @NotEmpty
    private String houseProperty;

    @NotEmpty
    private String province;

    @NotEmpty
    private String city;

    @NotEmpty
    private String area;

    private String street;

    //住址
    @NotEmpty
    private String address;

    private Boolean hasHouse;

    private Boolean hasOutHouse;

    private Boolean tobaccoAuth;

    //是否实名
    private Boolean identityAuth;

    /**
     * 身份证背面
     */
    private String idCardFrontPic;

    /**
     * 身份证背面
     */
    private Long idCardFrontPicId;

    /**
     * 身份证正面
     */
    private String idCardBackPic;

    /**
     * 身份证正面Id
     */
    private Long idCardBackPicId;

    /**
     * 手持身份证
     */
    @NotEmpty
    private String heldIdCardPic;

    /**
     * 手持身份证Id
     */
    private Long heldIdCardPicId;

    /**
     * 房产证照片
     */
    private String houseCertificatePic;

    /**
     * 房产证照片Id
     */
    private Long houseCertificatePicId;

    /**
     * 结婚证照片
     */
    private String marryCertificatePic;

    /**
     * 结婚证照片Id
     */
    private Long marryCertificatePicId;

    /**
     * 户口本照片
     */
    private String residenceBookletPic;

    /**
     * 户口本照片Id
     */
    private Long residenceBookletPicId;

    /**
     * 是否活体
     */
    private Boolean lived;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getMarryStatus() {
        return marryStatus;
    }

    public void setMarryStatus(String marryStatus) {
        this.marryStatus = marryStatus;
    }

    public String getHouseProperty() {
        return houseProperty;
    }

    public void setHouseProperty(String houseProperty) {
        this.houseProperty = houseProperty;
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

    public boolean isHasOutHouse() {
        return hasOutHouse;
    }

    public void setHasOutHouse(boolean hasOutHouse) {
        this.hasOutHouse = hasOutHouse;
    }

    public boolean isHasHouse() {
        return hasHouse;
    }

    public void setHasHouse(boolean hasHouse) {
        this.hasHouse = hasHouse;
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

    public Boolean getLived() {
        return lived;
    }

    public void setLived(Boolean lived) {
        this.lived = lived;
    }

    public Boolean getTobaccoAuth() {
        return tobaccoAuth;
    }

    public void setTobaccoAuth(Boolean tobaccoAuth) {
        this.tobaccoAuth = tobaccoAuth;
    }

    public Boolean getIdentityAuth() {
        return identityAuth;
    }

    public void setIdentityAuth(Boolean identityAuth) {
        this.identityAuth = identityAuth;
    }
}
