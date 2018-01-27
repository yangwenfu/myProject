package com.xinyunlian.jinfu.center.dto;

import java.io.Serializable;

/**
 * Created by King on 2017/5/10.
 */
public class CenterUserDto implements Serializable{
    private static final long serialVersionUID = -1825019626467155375L;
    private String userId;
    //唯一编号
    private String uuid;
    //用户名
    private String username;
    //手机
    private String mobile;
    //性别（1男，2女）
    private Integer gender;
    //生日
    private String brithday;
    //真实姓名
    private String realName;
    //身份证
    private String idCardNumber;
    //身份证有效开始日期
    private String idCardStart;
    //身份证有效结束日期
    private String idCardEnd;
    //家庭电话
    private String homeAddress;
    // 当前居住地址
    private String homePhone;
    //省
    private String province;
    //市
    private String city;
    //区
    private String area;
    //街道
    private String street;

    private Long cityId;

    private Long provinceId;

    private Long streetId;

    private String streetGbCode;
    //区id
    private Long areaId;
    //区gbcode
    private String areaGbCode;
    //email
    private String email;
    //微信openId
    private String openId;
    //微信（三方登陆）
    private String wechat;
    //QQ(三方登陆)
    private String qq;
    //支付宝(三方登陆)
    private String alipay;
    //来源
    private String source = "金服";
    //备注
    private String remark;
    //是否锁
    private Integer isLock;
    // 是否启用
    private Integer isEnabled;
    //是否实名认证
    private Integer identityAuth;
    //实名认证时间
    private String identityAuthDate;
    //会员成长值
    private Integer growth;

    private Long point;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getBrithday() {
        return brithday;
    }

    public void setBrithday(String brithday) {
        this.brithday = brithday;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    public String getIdCardStart() {
        return idCardStart;
    }

    public void setIdCardStart(String idCardStart) {
        this.idCardStart = idCardStart;
    }

    public String getIdCardEnd() {
        return idCardEnd;
    }

    public void setIdCardEnd(String idCardEnd) {
        this.idCardEnd = idCardEnd;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
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

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getAreaGbCode() {
        return areaGbCode;
    }

    public void setAreaGbCode(String areaGbCode) {
        this.areaGbCode = areaGbCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsLock() {
        return isLock;
    }

    public void setIsLock(Integer isLock) {
        this.isLock = isLock;
    }

    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Integer getIdentityAuth() {
        return identityAuth;
    }

    public void setIdentityAuth(Integer identityAuth) {
        this.identityAuth = identityAuth;
    }

    public String getIdentityAuthDate() {
        return identityAuthDate;
    }

    public void setIdentityAuthDate(String identityAuthDate) {
        this.identityAuthDate = identityAuthDate;
    }

    public Integer getGrowth() {
        return growth;
    }

    public void setGrowth(Integer growth) {
        this.growth = growth;
    }

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public Long getStreetId() {
        return streetId;
    }

    public void setStreetId(Long streetId) {
        this.streetId = streetId;
    }

    public String getStreetGbCode() {
        return streetGbCode;
    }

    public void setStreetGbCode(String streetGbCode) {
        this.streetGbCode = streetGbCode;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }
}
