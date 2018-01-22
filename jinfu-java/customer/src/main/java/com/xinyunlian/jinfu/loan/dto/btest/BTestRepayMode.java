package com.xinyunlian.jinfu.loan.dto.btest;

import com.xinyunlian.jinfu.loan.enums.ERepayMode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/24 0024.
 */
public class BTestRepayMode implements Serializable {

    /**
     * 产品编号
     */
    private String prodId;

    /**
     * 还款方式名称
     */
    private String name;

    /**
     * 还款期限单位
     */
    private String unit;

    /**
     * 可选期限
     */
    private List<Integer> periods;

    /**
     * 自定义利率描述
     */
    private String customInterestType;

    /**
     * 自定义利率值
     */
    private String customInterest;

    /**
     * 日利率
     */
    private BigDecimal rate;

    /**
     * 还款方式
     */
    private ERepayMode repayMode;

    /**
     * 自定义描述信息
     */
    private String desc;

    public ERepayMode getRepayMode() {
        return repayMode;
    }

    public void setRepayMode(ERepayMode repayMode) {
        this.repayMode = repayMode;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

