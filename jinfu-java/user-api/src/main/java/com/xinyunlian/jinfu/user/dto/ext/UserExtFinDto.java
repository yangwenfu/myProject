package com.xinyunlian.jinfu.user.dto.ext;

import com.xinyunlian.jinfu.user.enums.EIncomeSource;

import java.math.BigDecimal;

/**
 * Created by King on 2017/2/17.
 */
public class UserExtFinDto extends UserExtIdDto{
    private static final long serialVersionUID = 5812211079965426664L;
    //个人主要收入来源
    private EIncomeSource incomeSource;
    //个人月收入
    private BigDecimal incomeMonth;
    //个人年收入
    private BigDecimal incomeYear;
    //家庭主要收入来源
    private EIncomeSource incomeFamSource;
    //家庭月收入
    private BigDecimal incomeFamMonth;
    //家庭年收入
    private BigDecimal incomeFamYear;
    //贷款总额
    private BigDecimal loanAmount;
    //贷款笔数
    private Integer loanNum;
    //月还款额
    private BigDecimal repayMonth;

    private Integer houseNum;

    private Integer carNum;

    private Integer creditCardNum;

    public EIncomeSource getIncomeSource() {
        return incomeSource;
    }

    public void setIncomeSource(EIncomeSource incomeSource) {
        this.incomeSource = incomeSource;
    }

    public void setIncomeFamSource(EIncomeSource incomeFamSource) {
        this.incomeFamSource = incomeFamSource;
    }

    public BigDecimal getIncomeMonth() {
        return incomeMonth;
    }

    public void setIncomeMonth(BigDecimal incomeMonth) {
        this.incomeMonth = incomeMonth;
    }

    public BigDecimal getIncomeYear() {
        return incomeYear;
    }

    public void setIncomeYear(BigDecimal incomeYear) {
        this.incomeYear = incomeYear;
    }

    public BigDecimal getIncomeFamMonth() {
        return incomeFamMonth;
    }

    public void setIncomeFamMonth(BigDecimal incomeFamMonth) {
        this.incomeFamMonth = incomeFamMonth;
    }

    public BigDecimal getIncomeFamYear() {
        return incomeFamYear;
    }

    public void setIncomeFamYear(BigDecimal incomeFamYear) {
        this.incomeFamYear = incomeFamYear;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getLoanNum() {
        return loanNum;
    }

    public void setLoanNum(Integer loanNum) {
        this.loanNum = loanNum;
    }

    public BigDecimal getRepayMonth() {
        return repayMonth;
    }

    public void setRepayMonth(BigDecimal repayMonth) {
        this.repayMonth = repayMonth;
    }

    public EIncomeSource getIncomeFamSource() {
        return incomeFamSource;
    }

    public Integer getHouseNum() {
        return houseNum;
    }

    public void setHouseNum(Integer houseNum) {
        this.houseNum = houseNum;
    }

    public Integer getCarNum() {
        return carNum;
    }

    public void setCarNum(Integer carNum) {
        this.carNum = carNum;
    }

    public Integer getCreditCardNum() {
        return creditCardNum;
    }

    public void setCreditCardNum(Integer creditCardNum) {
        this.creditCardNum = creditCardNum;
    }
}
