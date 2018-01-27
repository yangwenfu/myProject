package com.xinyunlian.jinfu.loan.dto;

import java.io.Serializable;

/**
 * Created by Willwang on 2017/2/23.
 */
public class LoanApplUserDto implements Serializable {

    private String applId;

    /**
     * 包含用户基础数据、家庭、工作状况、收入、负债、社保、公积金
     */
    private String userBase;

    /**
     * 联系人信息
     */
    private String userLinkman;

    /**
     * 店铺数据
     * @return
     */
    private String storeBase;

    /**
     * 房产信息
     */
    private String houseBase;

    /**
     * 车辆信息
     */
    private String carBase;

    /**
     * 银行流水
     */
    private String bankBase;

    /**
     * 烟草爬虫信息
     */
    private String userTobacco;

    /**
     * 征信电话号码信息
     */
    private String creditPhone;

    /**
     * 征信逾期信息
     */
    private String creditOverdue;

    /**
     * 征信贷款信息
     */
    private String creditLoan;

    /**
     * 征信黑名单信息
     */
    private String creditBlacklist;

    /**
     * 征信其他机构查询情况
     */
    private String creditStatistics;

    public String getCreditPhone() {
        return creditPhone;
    }

    public void setCreditPhone(String creditPhone) {
        this.creditPhone = creditPhone;
    }

    public String getCreditOverdue() {
        return creditOverdue;
    }

    public void setCreditOverdue(String creditOverdue) {
        this.creditOverdue = creditOverdue;
    }

    public String getCreditLoan() {
        return creditLoan;
    }

    public void setCreditLoan(String creditLoan) {
        this.creditLoan = creditLoan;
    }

    public String getCreditBlacklist() {
        return creditBlacklist;
    }

    public void setCreditBlacklist(String creditBlacklist) {
        this.creditBlacklist = creditBlacklist;
    }

    public String getCreditStatistics() {
        return creditStatistics;
    }

    public void setCreditStatistics(String creditStatistics) {
        this.creditStatistics = creditStatistics;
    }

    public String getUserTobacco() {
        return userTobacco;
    }

    public void setUserTobacco(String userTobacco) {
        this.userTobacco = userTobacco;
    }

    public String getBankBase() {
        return bankBase;
    }

    public void setBankBase(String bankBase) {
        this.bankBase = bankBase;
    }

    public String getHouseBase() {
        return houseBase;
    }

    public void setHouseBase(String houseBase) {
        this.houseBase = houseBase;
    }

    public String getCarBase() {
        return carBase;
    }

    public void setCarBase(String carBase) {
        this.carBase = carBase;
    }

    public String getStoreBase() {
        return storeBase;
    }

    public void setStoreBase(String storeBase) {
        this.storeBase = storeBase;
    }

    public String getUserLinkman() {
        return userLinkman;
    }

    public void setUserLinkman(String userLinkman) {
        this.userLinkman = userLinkman;
    }

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }

    public String getUserBase() {
        return userBase;
    }

    public void setUserBase(String userBase) {
        this.userBase = userBase;
    }
}
