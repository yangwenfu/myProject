package com.xinyunlian.jinfu.loan.dto.user;

import java.io.Serializable;

/**
 * @author willwang
 */
@Deprecated
public class LoanUserBaseDto implements Serializable{

    //用户名字
    private String userName;

    //身份证号
    private String idCardNo;

    //婚姻状态
    private String marryStatus;

    //手机
    private String mobile;

    //联系电话
    private String phone;

    //住宅性质
    private String houseProperty;

    private String province;

    private String city;

    private String area;

    private String street;

    //住址
    private String address;

    private boolean hasHouse;

    private boolean hasOutHouse;

    private Boolean lived;

    private Boolean tobaccoAuth;

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

    private Boolean yituPass;

    private Double yituSimilarity;

    private String linePicture;

    private String livePicture;

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

    public String getLinePicture() {
        return linePicture;
    }

    public void setLinePicture(String linePicture) {
        this.linePicture = linePicture;
    }

    public String getLivePicture() {
        return livePicture;
    }

    public void setLivePicture(String livePicture) {
        this.livePicture = livePicture;
    }
}
