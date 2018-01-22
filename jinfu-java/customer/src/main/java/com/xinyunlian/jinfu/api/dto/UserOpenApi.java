package com.xinyunlian.jinfu.api.dto;

import com.xinyunlian.jinfu.api.validate.dto.OpenApiBaseDto;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by KimLL on 2016/8/18.
 */

public class UserOpenApi extends OpenApiBaseDto {
    private static final long serialVersionUID = 1L;
    private String userId;
    //手机
    @Length(min=0, max=11)
    @NotBlank(message = "手机号不能为空")
    private String mobile;
    //用户名字
    private String userName;
    //身份证号
    private String idCardNo;
    //实名认证，默认0，1为已认证
    private Boolean identityAuth;

    @Length(min=0, max=64)
    @NotBlank(message = "省不能为空")
    private String province;

    @Length(min=0, max=64)
    @NotBlank(message = "市不能为空")
    private String city;

    @Length(min=0, max=64)
    @NotBlank(message = "区不能为空")
    private String area;

    private String street;

    @Length(min=0, max=255)
    @NotBlank(message = "店铺名不能为空")
    private String storeName;

    @Length(min=0, max=50)
    @NotBlank(message = "烟草许可证号不能为空")
    private String tobaccoCertificateNo;

    @Length(min=0, max=500)
    @NotBlank(message = "地址不能为空")
    private String address;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public Boolean getIdentityAuth() {
        return identityAuth;
    }

    public void setIdentityAuth(Boolean identityAuth) {
        this.identityAuth = identityAuth;
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getTobaccoCertificateNo() {
        return tobaccoCertificateNo;
    }

    public void setTobaccoCertificateNo(String tobaccoCertificateNo) {
        this.tobaccoCertificateNo = tobaccoCertificateNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
