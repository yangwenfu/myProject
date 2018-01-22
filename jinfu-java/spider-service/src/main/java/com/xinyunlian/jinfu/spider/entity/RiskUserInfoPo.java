package com.xinyunlian.jinfu.spider.entity;


import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * Created by menglei on 2016年11月07日.
 */
@Entity
@Table(name = "risk_user_info")
public class RiskUserInfoPo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "USER_NAME")
    private String username;//账户

    @Column(name = "PASSWORD")
    private String password;//密码

    @Column(name = "province_id")
    private Long provinceId;

    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "area_id")
    private Long areaId;

    @Column(name = "NICK_NAME")
    private String nickname;//昵称

    @Column(name = "USER_TYPE")
    private String userType;//用户类型

    @Column(name = "USER_COMPANY")
    private String userCompany;//所属公司

    @Column(name = "USER_CODE")
    private String userCode;//对应人员编码

    @Column(name = "PHONE")
    private String phone;//电话

    @Column(name = "MOBILE")
    private String mobile;//手机

    @Column(name = "EMAIL")
    private String email;//邮箱

    @Column(name = "QQ")
    private String qq;//qq

    @Column(name = "BIRTHDAY")
    private String birthday;//生日

    @Column(name = "MengID")
    private String mengId;//盟号

    @Column(name = "CUST_NAME")
    private String custName;//客户名称

    @Column(name = "SHORT_NAME")
    private String shortName;//客户简称

    @Column(name = "CUST_CODE")
    private String custCode;//客户编码

    @Column(name = "SHORT_CODE")
    private String shortCode;//客户简码'

    @Column(name = "LICENSE_CODE")
    private String licenseCode;//许可证号

    @Column(name = "ORDER_PHONE")
    private String orderPhone;//客户电话

    @Column(name = "MANAGER")
    private String manager;//负责人

    @Column(name = "MANAGER_PHONE")
    private String managerPhone;//负责人电话

    @Column(name = "ADDRESS")
    private String address;//地址

    @Column(name = "SALE_CENTER")
    private String saleCenter;//营销中心

    @Column(name = "SALE_DEPT")
    private String saleDept;//营销部

    @Column(name = "SALE_MANAGE")
    private String saleManage;//客户经理

    @Column(name = "SALE_MANAGE_PHONE")
    private String saleManagePhone;//客户经理联系电话

    @Column(name = "RETAIL_TYPE")
    private String retailType;//零售业态

    @Column(name = "MARKET_TYPE")
    private String marketType;//市场类别

    @Column(name = "CUST_SIZE")
    private String custSize;//经营规模

    @Column(name = "CUST_TYPE")
    private String custType;//客户类别

    @Column(name = "PERIODS")
    private String periods;//订货周期

    @Column(name = "GEAR")
    private String gear;//档位

    @Column(name = "ACCOUNT_BANK")
    private String accountBank;//开户银行

    @Column(name = "ACCOUNT_ID")
    private String accountId;//开户账号

    @Column(name = "LAST_OPERATION_STATUS")
    private String lastOperationStatus;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserCompany() {
        return userCompany;
    }

    public void setUserCompany(String userCompany) {
        this.userCompany = userCompany;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getMengId() {
        return mengId;
    }

    public void setMengId(String mengId) {
        this.mengId = mengId;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getLicenseCode() {
        return licenseCode;
    }

    public void setLicenseCode(String licenseCode) {
        this.licenseCode = licenseCode;
    }

    public String getOrderPhone() {
        return orderPhone;
    }

    public void setOrderPhone(String orderPhone) {
        this.orderPhone = orderPhone;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public void setManagerPhone(String managerPhone) {
        this.managerPhone = managerPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSaleCenter() {
        return saleCenter;
    }

    public void setSaleCenter(String saleCenter) {
        this.saleCenter = saleCenter;
    }

    public String getSaleDept() {
        return saleDept;
    }

    public void setSaleDept(String saleDept) {
        this.saleDept = saleDept;
    }

    public String getSaleManage() {
        return saleManage;
    }

    public void setSaleManage(String saleManage) {
        this.saleManage = saleManage;
    }

    public String getSaleManagePhone() {
        return saleManagePhone;
    }

    public void setSaleManagePhone(String saleManagePhone) {
        this.saleManagePhone = saleManagePhone;
    }

    public String getRetailType() {
        return retailType;
    }

    public void setRetailType(String retailType) {
        this.retailType = retailType;
    }

    public String getMarketType() {
        return marketType;
    }

    public void setMarketType(String marketType) {
        this.marketType = marketType;
    }

    public String getCustSize() {
        return custSize;
    }

    public void setCustSize(String custSize) {
        this.custSize = custSize;
    }

    public String getCustType() {
        return custType;
    }

    public void setCustType(String custType) {
        this.custType = custType;
    }

    public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }

    public String getGear() {
        return gear;
    }

    public void setGear(String gear) {
        this.gear = gear;
    }

    public String getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getLastOperationStatus() {
        return lastOperationStatus;
    }

    public void setLastOperationStatus(String lastOperationStatus) {
        this.lastOperationStatus = lastOperationStatus;
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

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

}


