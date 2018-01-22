package com.xinyunlian.jinfu.loan.dto.resp.apply;

import com.xinyunlian.jinfu.audit.dto.LoanAuditDto;
import com.xinyunlian.jinfu.loan.enums.EApplStatus;
import com.xinyunlian.jinfu.loan.enums.ERepayMode;
import com.xinyunlian.jinfu.loan.enums.ETermType;
import com.xinyunlian.jinfu.product.enums.EFineType;
import com.xinyunlian.jinfu.product.enums.EIntrRateType;
import com.xinyunlian.jinfu.product.enums.ELoanProductType;
import com.xinyunlian.jinfu.product.enums.EViolateType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author willwang
 * 后台贷款申请详情
 */
public class LoanApplyDetailDto implements Serializable{

    //申请编号
    private String applId;

    //申请时间
    private String date;

    //申请地区
    private String address;

    //申请金额
    private BigDecimal applAmt;

    //申请期限类型
    private ETermType termType;

    //申请期限
    private Integer termLen;

    //利率类型
    private EIntrRateType rateType;

    //利率
    private BigDecimal rate;

    //还款方式
    private ERepayMode repayMode;

    //产品编号
    private String productId;

    //产品名称
    private String productName;

    //产品类型
    private ELoanProductType productType;

    //违约金类型
    private EViolateType violateType;

    //违约金金额
    private BigDecimal violateValue;

    //罚息
    private EFineType fineType;

    private BigDecimal fineValue;

    //申请类型
    private EApplStatus status;

    //可选的期限类型
    private List<String> termLenList = new ArrayList<>();

    private LoanAuditDto trial;

    private LoanAuditDto review;

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getApplAmt() {
        return applAmt;
    }

    public void setApplAmt(BigDecimal applAmt) {
        this.applAmt = applAmt;
    }

    public ETermType getTermType() {
        return termType;
    }

    public void setTermType(ETermType termType) {
        this.termType = termType;
    }

    public Integer getTermLen() {
        return termLen;
    }

    public void setTermLen(Integer termLen) {
        this.termLen = termLen;
    }

    public EIntrRateType getRateType() {
        return rateType;
    }

    public void setRateType(EIntrRateType rateType) {
        this.rateType = rateType;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public ERepayMode getRepayMode() {
        return repayMode;
    }

    public void setRepayMode(ERepayMode repayMode) {
        this.repayMode = repayMode;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public ELoanProductType getProductType() {
        return productType;
    }

    public void setProductType(ELoanProductType productType) {
        this.productType = productType;
    }

    public EViolateType getViolateType() {
        return violateType;
    }

    public void setViolateType(EViolateType violateType) {
        this.violateType = violateType;
    }

    public BigDecimal getViolateValue() {
        return violateValue;
    }

    public void setViolateValue(BigDecimal violateValue) {
        this.violateValue = violateValue;
    }

    public EFineType getFineType() {
        return fineType;
    }

    public void setFineType(EFineType fineType) {
        this.fineType = fineType;
    }

    public BigDecimal getFineValue() {
        return fineValue;
    }

    public void setFineValue(BigDecimal fineValue) {
        this.fineValue = fineValue;
    }

    public EApplStatus getStatus() {
        return status;
    }

    public void setStatus(EApplStatus status) {
        this.status = status;
    }

    public LoanAuditDto getTrial() {
        return trial;
    }

    public void setTrial(LoanAuditDto trial) {
        this.trial = trial;
    }

    public LoanAuditDto getReview() {
        return review;
    }

    public void setReview(LoanAuditDto review) {
        this.review = review;
    }

    public List<String> getTermLenList() {
        return termLenList;
    }

    public void setTermLenList(List<String> termLenList) {
        this.termLenList = termLenList;
    }

    public void addTermLen(String termLen){
        this.termLenList.add(termLen);
    }
}
