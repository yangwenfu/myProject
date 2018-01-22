package com.xinyunlian.jinfu.loan.dto.resp.credit;

import com.xinyunlian.jinfu.loan.enums.ERepayMode;
import com.xinyunlian.jinfu.loan.enums.ETermType;
import com.xinyunlian.jinfu.product.enums.EIntrRateType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户申请信贷类产品时展示给用户的信息
 * @author willwang
 */
public class LoanApplCredit implements Serializable {

    /**
     * 产品编号
     */
    private String productId;

    /**
     * 还款方式别名
     */
    private String repayModeAlias;

    /**
     * 最小贷款金额
     */
    private BigDecimal loanAmtMin;

    /**
     * 最大贷款金额
     */
    private BigDecimal loanAmtMax;

    /**
     * 利率类型
     */
    private EIntrRateType intrRateType;

    /**
     * 利率
     */
    private BigDecimal intrRate;

    /**
     * 期限单位
     */
    private String periodUnit;

    /**
     * 期限类型
     */
    private ETermType termType;

    /**
     * 期限可选择的内容
     */
    private List<Integer> termLenList = new ArrayList<>();

    /**
     * 还款类型
     */
    private ERepayMode repayMode;

    public String getRepayModeAlias() {
        return repayModeAlias;
    }

    public void setRepayModeAlias(String repayModeAlias) {
        this.repayModeAlias = repayModeAlias;
    }

    public BigDecimal getLoanAmtMin() {
        return loanAmtMin;
    }

    public void setLoanAmtMin(BigDecimal loanAmtMin) {
        this.loanAmtMin = loanAmtMin;
    }

    public BigDecimal getLoanAmtMax() {
        return loanAmtMax;
    }

    public void setLoanAmtMax(BigDecimal loanAmtMax) {
        this.loanAmtMax = loanAmtMax;
    }

    public EIntrRateType getIntrRateType() {
        return intrRateType;
    }

    public void setIntrRateType(EIntrRateType intrRateType) {
        this.intrRateType = intrRateType;
    }

    public BigDecimal getIntrRate() {
        return intrRate;
    }

    public void setIntrRate(BigDecimal intrRate) {
        this.intrRate = intrRate;
    }

    public ETermType getTermType() {
        return termType;
    }

    public void setTermType(ETermType termType) {
        this.termType = termType;
    }

    public List<Integer> getTermLenList() {
        return termLenList;
    }

    public void setTermLenList(List<Integer> termLenList) {
        this.termLenList = termLenList;
    }

    public void addTermLen(Integer termLen){
        this.termLenList.add(termLen);
    }

    public ERepayMode getRepayMode() {
        return repayMode;
    }

    public void setRepayMode(ERepayMode repayMode) {
        this.repayMode = repayMode;
        this.repayModeAlias = repayMode.getAlias();
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getPeriodUnit() {
        return periodUnit;
    }

    public void setPeriodUnit(String periodUnit) {
        this.periodUnit = periodUnit;
    }
}
