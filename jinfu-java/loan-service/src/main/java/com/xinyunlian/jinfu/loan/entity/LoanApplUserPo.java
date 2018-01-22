package com.xinyunlian.jinfu.loan.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.loan.enums.ELoanStat;
import com.xinyunlian.jinfu.loan.enums.ERepayMode;
import com.xinyunlian.jinfu.loan.enums.ETermType;
import com.xinyunlian.jinfu.loan.enums.ETransferStat;
import com.xinyunlian.jinfu.loan.enums.converter.ELoanStatEnumConverter;
import com.xinyunlian.jinfu.loan.enums.converter.ERepayModeEnumConverter;
import com.xinyunlian.jinfu.loan.enums.converter.ETermTypeEnumConverter;
import com.xinyunlian.jinfu.loan.enums.converter.ETransferStatEnumConverter;
import com.xinyunlian.jinfu.product.enums.EIntrRateType;
import com.xinyunlian.jinfu.product.enums.converter.EIntrRateTypeEnumConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "FP_LOAN_APPL_USER")
public class LoanApplUserPo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "APPL_ID")
    private String applId;

    @Column(name = "USER_BASE")
    private String userBase;

    @Column(name = "USER_LINKMAN")
    private String userLinkman;

    @Column(name = "STORE_BASE")
    private String storeBase;

    @Column(name = "HOUSE_BASE")
    private String houseBase;

    @Column(name = "CAR_BASE")
    private String carBase;

    @Column(name="BANK_BASE")
    private String bankBase;

    @Column(name = "USER_TOBACCO")
    private String userTobacco;

    @Column(name = "CREDIT_PHONE")
    private String creditPhone;

    @Column(name = "CREDIT_OVERDUE")
    private String creditOverdue;

    @Column(name = "CREDIT_LOAN")
    private String creditLoan;

    @Column(name = "CREDIT_BLACKLIST")
    private String creditBlacklist;

    @Column(name = "CREDIT_STATISTICS")
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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getBankBase() {
        return bankBase;
    }

    public void setBankBase(String bankBase) {
        this.bankBase = bankBase;
    }

    public String getCarBase() {
        return carBase;
    }

    public void setCarBase(String carBase) {
        this.carBase = carBase;
    }

    public String getHouseBase() {
        return houseBase;
    }

    public void setHouseBase(String houseBase) {
        this.houseBase = houseBase;
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
