package com.xinyunlian.jinfu.risk.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

@Entity
@Table(name = "RISK_USER_INFO")
public class RiskUserInfoPo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户编号
     */
    @Column(name = "USER_ID")
    private String userId;

    /**
     * 新商盟账户信息
     */
    @Column(name = "ACCT_NO")
    private String acctNo;

    /**
     * 昵称/客户名称
     */
    @Column(name = "STORE_NAME")
    private String storeName;

    /**
     * 用户类型
     */
    @Column(name = "ACCT_TYPE")
    private String acctType;

    /**
     * 所属公司
     */
    @Column(name = "COMPANY")
    private String company;

    /**
     * 对应人员编码
     */
    @Column(name = "USER_CODE")
    private String userCode;

    /**
     * 电话
     */
    @Column(name = "PHONE")
    private String phone;

    /**
     * 手机
     */
    @Column(name = "MOBILE")
    private String mobile;

    /**
     * 盟号
     */
    @Column(name = "MENG_NO")
    private String mengNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public String getMengNo() {
        return mengNo;
    }

    public void setMengNo(String mengNo) {
        this.mengNo = mengNo;
    }
}
