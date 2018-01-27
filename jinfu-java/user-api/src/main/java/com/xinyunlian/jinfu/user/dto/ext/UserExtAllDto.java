package com.xinyunlian.jinfu.user.dto.ext;

import com.xinyunlian.jinfu.user.enums.EHouseProperty;
import com.xinyunlian.jinfu.user.enums.EIncomeSource;
import com.xinyunlian.jinfu.user.enums.EMarryStatus;
import com.xinyunlian.jinfu.user.enums.ESalaryMode;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Willwang on 2017/3/8.
 */
public class UserExtAllDto extends UserExtBaseDto{

    //婚姻状况
    private EMarryStatus marryStatus;
    //家庭人口数
    private Integer familyNum;
    //子女人数
    private Integer childrenNum;
    //供养人数
    private Integer supportNum;
    //受薪形式
    private ESalaryMode salaryMode;
    //单位地址
    private String companyAdd;
    //工作单位
    private String company;
    //单位性质
    private String companyType;
    //企业规模
    private String companySize;
    //职务
    private String position;
    //单位电话
    private String companyPhone;
    //入职时间
    private Date employDate;

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

    //社保单位名称
    private String ssName;
    //社保缴存基数
    private BigDecimal ssPayBase;
    //缴至年月
    private Date ssPayDate;
    //公积金单位名称
    private String hfName;
    //公积金开户日期
    private Date hfOpenDate;
    //公积金缴至年月
    private Date hfPayDate;
    //公积金月缴额度
    private BigDecimal hfPayBase;
    //公积金查询日期
    private Date hfQueryDate;
    //公积金查询时余额
    private BigDecimal hfAmount;

    public EMarryStatus getMarryStatus() {
        return marryStatus;
    }

    public void setMarryStatus(EMarryStatus marryStatus) {
        this.marryStatus = marryStatus;
    }

    public Integer getFamilyNum() {
        return familyNum;
    }

    public void setFamilyNum(Integer familyNum) {
        this.familyNum = familyNum;
    }

    public Integer getChildrenNum() {
        return childrenNum;
    }

    public void setChildrenNum(Integer childrenNum) {
        this.childrenNum = childrenNum;
    }

    public Integer getSupportNum() {
        return supportNum;
    }

    public void setSupportNum(Integer supportNum) {
        this.supportNum = supportNum;
    }

    public ESalaryMode getSalaryMode() {
        return salaryMode;
    }

    public void setSalaryMode(ESalaryMode salaryMode) {
        this.salaryMode = salaryMode;
    }

    public String getCompanyAdd() {
        return companyAdd;
    }

    public void setCompanyAdd(String companyAdd) {
        this.companyAdd = companyAdd;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public Date getEmployDate() {
        return employDate;
    }

    public void setEmployDate(Date employDate) {
        this.employDate = employDate;
    }

    public EIncomeSource getIncomeSource() {
        return incomeSource;
    }

    public void setIncomeSource(EIncomeSource incomeSource) {
        this.incomeSource = incomeSource;
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

    public EIncomeSource getIncomeFamSource() {
        return incomeFamSource;
    }

    public void setIncomeFamSource(EIncomeSource incomeFamSource) {
        this.incomeFamSource = incomeFamSource;
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

    public String getSsName() {
        return ssName;
    }

    public void setSsName(String ssName) {
        this.ssName = ssName;
    }

    public BigDecimal getSsPayBase() {
        return ssPayBase;
    }

    public void setSsPayBase(BigDecimal ssPayBase) {
        this.ssPayBase = ssPayBase;
    }

    public Date getSsPayDate() {
        return ssPayDate;
    }

    public void setSsPayDate(Date ssPayDate) {
        this.ssPayDate = ssPayDate;
    }

    public String getHfName() {
        return hfName;
    }

    public void setHfName(String hfName) {
        this.hfName = hfName;
    }

    public Date getHfOpenDate() {
        return hfOpenDate;
    }

    public void setHfOpenDate(Date hfOpenDate) {
        this.hfOpenDate = hfOpenDate;
    }

    public Date getHfPayDate() {
        return hfPayDate;
    }

    public void setHfPayDate(Date hfPayDate) {
        this.hfPayDate = hfPayDate;
    }

    public BigDecimal getHfPayBase() {
        return hfPayBase;
    }

    public void setHfPayBase(BigDecimal hfPayBase) {
        this.hfPayBase = hfPayBase;
    }

    public Date getHfQueryDate() {
        return hfQueryDate;
    }

    public void setHfQueryDate(Date hfQueryDate) {
        this.hfQueryDate = hfQueryDate;
    }

    public BigDecimal getHfAmount() {
        return hfAmount;
    }

    public void setHfAmount(BigDecimal hfAmount) {
        this.hfAmount = hfAmount;
    }
}
