package com.xinyunlian.jinfu.user.dto.ext;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by King on 2017/2/17.
 */
public class UserExtFundDto extends UserExtIdDto{
    private static final long serialVersionUID = 135559273974812447L;
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
