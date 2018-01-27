package com.xinyunlian.jinfu.external.dto.req;

import java.io.Serializable;

/**
 * Created by godslhand on 2017/6/19.
 */
public class RegisterReq implements Serializable {
    private String idNo; //身份证号
    private String userName; //用户姓名
    private String contactTel; //联系电话
    private String registerTime; //注册时间
    private String userIdResource;// 用户来源平台中的唯一标识
    private String registerName;// 营业执照估时间注册名称
    private String legalRepName;//法人代表名称
    private String legalRepId;//法人代表身份证
    private String legalRepMobile;//法人代表手机
    private String legalRepAdd;//法人代表家庭住址
    private String actualAdd;//店铺地址
    private String bussinesLicencePhoto;// 营业执照
    private String taxRegistrationPhoto;//烟草许可证
    private String organizationCodePhoto;//身份证正面照片
    private String idPhotoFront;//本人手持身份证照片
    private String outsidePhoto;//门头照片
    private String insidePhoto;//店内照
    private String notes;//其他补充信息

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContactTel() {
        return contactTel;
    }

    public void setContactTel(String contactTel) {
        this.contactTel = contactTel;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getUserIdResource() {
        return userIdResource;
    }

    public void setUserIdResource(String userIdResource) {
        this.userIdResource = userIdResource;
    }

    public String getRegisterName() {
        return registerName;
    }

    public void setRegisterName(String registerName) {
        this.registerName = registerName;
    }

    public String getLegalRepName() {
        return legalRepName;
    }

    public void setLegalRepName(String legalRepName) {
        this.legalRepName = legalRepName;
    }

    public String getLegalRepId() {
        return legalRepId;
    }

    public void setLegalRepId(String legalRepId) {
        this.legalRepId = legalRepId;
    }

    public String getLegalRepMobile() {
        return legalRepMobile;
    }

    public void setLegalRepMobile(String legalRepMobile) {
        this.legalRepMobile = legalRepMobile;
    }

    public String getLegalRepAdd() {
        return legalRepAdd;
    }

    /**
     * 法人代表家庭地址
     */
    public void setLegalRepAdd(String legalRepAdd) {
        this.legalRepAdd = legalRepAdd;
    }

    public String getActualAdd() {
        return actualAdd;
    }

    public void setActualAdd(String actualAdd) {
        this.actualAdd = actualAdd;
    }

    public String getBussinesLicencePhoto() {
        return bussinesLicencePhoto;
    }

    public void setBussinesLicencePhoto(String bussinesLicencePhoto) {
        this.bussinesLicencePhoto = bussinesLicencePhoto;
    }

    public String getTaxRegistrationPhoto() {
        return taxRegistrationPhoto;
    }

    public void setTaxRegistrationPhoto(String taxRegistrationPhoto) {
        this.taxRegistrationPhoto = taxRegistrationPhoto;
    }

    public String getOrganizationCodePhoto() {
        return organizationCodePhoto;
    }

    public void setOrganizationCodePhoto(String organizationCodePhoto) {
        this.organizationCodePhoto = organizationCodePhoto;
    }

    public String getIdPhotoFront() {
        return idPhotoFront;
    }

    public void setIdPhotoFront(String idPhotoFront) {
        this.idPhotoFront = idPhotoFront;
    }

    public String getOutsidePhoto() {
        return outsidePhoto;
    }

    public void setOutsidePhoto(String outsidePhoto) {
        this.outsidePhoto = outsidePhoto;
    }

    public String getInsidePhoto() {
        return insidePhoto;
    }

    public void setInsidePhoto(String insidePhoto) {
        this.insidePhoto = insidePhoto;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "RegisterReq{" +
                "idNo='" + idNo + '\'' +
                ", userName='" + userName + '\'' +
                ", contactTel='" + contactTel + '\'' +
                ", registerTime='" + registerTime + '\'' +
                '}';
    }
}
