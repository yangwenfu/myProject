package com.xinyunlian.jinfu.loan.dto.product;

import com.xinyunlian.jinfu.loan.enums.ERepayMode;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Willwang on 2017/1/5.
 */
public class LoanProductDetailDto implements Serializable {

    /**
     * 产品编号
     */
    private String id;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 最高贷款额度
     */
    private BigDecimal loanAmtMax;

    /**
     * 最低贷款额度
     */
    private BigDecimal loanAmtMin;

    /**
     * 期限单位
     */
    private String unit;

    /**
     * 可选期限
     */
    private List<Integer> periods;

    /**
     * 利率
     */
    private BigDecimal rate;

    /**
     * 还款方式
     * @return
     */
    private ERepayMode repayMode;

    /**
     * 自定义利率类型
     */
    private String customInterestType;

    /**
     * 自定义利率值
     */
    private String customInterest;

    /**
     * 推荐人手机号
     */
    private String recUser;

    /**
     * 推荐人姓名
     */
    private String recName;

    /**
     * 默认金额
     */
    private Integer defaultAmt;

    /**
     * 期初服务费
     */
    @ApiModelProperty(value = "期初服务费")
    private String serviceFeeRt;

    /**
     * 月综合服务费
     */
    @ApiModelProperty(value = "月综合服务费")
    private List<BigDecimal> serviceFeeMonthRts = new ArrayList<>();

    public String getRecUser() {
        return recUser;
    }

    public void setRecUser(String recUser) {
        this.recUser = recUser;
    }

    public String getRecName() {
        return recName;
    }

    public void setRecName(String recName) {
        this.recName = recName;
    }

    public String getCustomInterestType() {
        return customInterestType;
    }

    public void setCustomInterestType(String customInterestType) {
        this.customInterestType = customInterestType;
    }

    public String getCustomInterest() {
        return customInterest;
    }

    public void setCustomInterest(String customInterest) {
        this.customInterest = customInterest;
    }

    public ERepayMode getRepayMode() {
        return repayMode;
    }

    public void setRepayMode(ERepayMode repayMode) {
        this.repayMode = repayMode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getLoanAmtMax() {
        return loanAmtMax;
    }

    public void setLoanAmtMax(BigDecimal loanAmtMax) {
        this.loanAmtMax = loanAmtMax;
    }

    public BigDecimal getLoanAmtMin() {
        return loanAmtMin;
    }

    public void setLoanAmtMin(BigDecimal loanAmtMin) {
        this.loanAmtMin = loanAmtMin;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<Integer> getPeriods() {
        return periods;
    }

    public void setPeriods(List<Integer> periods) {
        this.periods = periods;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getServiceFeeRt() {
        return serviceFeeRt;
    }

    public void setServiceFeeRt(String serviceFeeRt) {
        this.serviceFeeRt = serviceFeeRt;
    }

    public List<BigDecimal> getServiceFeeMonthRts() {
        return serviceFeeMonthRts;
    }

    public void setServiceFeeMonthRts(List<BigDecimal> serviceFeeMonthRts) {
        this.serviceFeeMonthRts = serviceFeeMonthRts;
    }

    public Integer getDefaultAmt() {
        return defaultAmt;
    }

    public void setDefaultAmt(Integer defaultAmt) {
        this.defaultAmt = defaultAmt;
    }
}
