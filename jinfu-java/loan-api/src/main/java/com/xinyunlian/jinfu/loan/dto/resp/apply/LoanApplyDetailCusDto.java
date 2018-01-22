package com.xinyunlian.jinfu.loan.dto.resp.apply;

import com.xinyunlian.jinfu.loan.enums.ERepayMode;
import com.xinyunlian.jinfu.schedule.enums.ELoanCustomerStatus;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 小贷2.0
 * Created by Willwang on 2017/1/10.
 */
public class LoanApplyDetailCusDto implements Serializable {

    /**
     * 申请编号
     */
    private String applId;

    /**
     * 贷款编号
     */
    private String loanId;

    /**
     * 产品编号
     */
    private String productId;

    /**
     * 还款期限
     */
    private Integer period;

    /**
     * 审批期限
     */
    private Integer auditPeriod;

    /**
     * 期限单位
     */
    private String unit;

    /**
     * 金额
     */
    private BigDecimal amt;

    /**
     * 审批金额
     */
    private BigDecimal auditAmt;

    /**
     * 申请时间
     */
    private String applDate;
    /**
     * 审批通过时间
     */
    private String auditDate;

    /**
     * 放款时间
     */
    private String transferDate;

    /**
     * 本期/到期还款日
     */
    private String repayDate;

    /**
     * 结清时间
     */
    private String paidDate;

    /**
     * 剩余未还本金
     */
    private BigDecimal surplus;

    /**
     * 贷款状态
     */
    private ELoanCustomerStatus status;

    /**
     * 还款方式
     */
    private ERepayMode repayMode;

    /**
     * 是否允许提前还款
     */
    private Boolean canPrepay;

    /**
     * 活动编号
     */
    private Long promoId;

    /**
     * 活动名称
     */
    private String promoName;

    /**
     * 活动详情
     */
    private String promoDesc;

    /**
     * 收款账户信息
     */
    private LoanBankDto bank;

    /**
     * 审核描述信息
     */
    private String auditDesc;

    /**
     * 是否已经签约
     *
     * @return
     */
    private Boolean signed;

    /**
     * 资金路由类型
     *
     * @return
     * @see com.xinyunlian.jinfu.router.enums.EFinanceSourceType
     */
    private String financeSourceType;

    /**
     * 是否存管
     */
    private Boolean depository;

    /**
     * 月服务费率
     */
    private BigDecimal serviceFeeMonthRt;


    /**
     * 月服务费率
     */
    private BigDecimal auditServiceFeeMonthRt;

    /**
     * 利息
     */
    private BigDecimal intrRt;

    public Boolean getDepository() {
        return depository;
    }

    public void setDepository(Boolean depository) {
        this.depository = depository;
    }

    public String getFinanceSourceType() {
        return financeSourceType;
    }

    public void setFinanceSourceType(String financeSourceType) {
        this.financeSourceType = financeSourceType;
    }

    public Boolean getSigned() {
        return signed;
    }

    public void setSigned(Boolean signed) {
        this.signed = signed;
    }

    public String getAuditDesc() {
        return auditDesc;
    }

    public void setAuditDesc(String auditDesc) {
        this.auditDesc = auditDesc;
    }

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getAuditPeriod() {
        return auditPeriod;
    }

    public void setAuditPeriod(Integer auditPeriod) {
        this.auditPeriod = auditPeriod;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getApplDate() {
        return applDate;
    }

    public void setApplDate(String applDate) {
        this.applDate = applDate;
    }

    public String getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(String auditDate) {
        this.auditDate = auditDate;
    }

    public String getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(String transferDate) {
        this.transferDate = transferDate;
    }

    public ELoanCustomerStatus getStatus() {
        return status;
    }

    public void setStatus(ELoanCustomerStatus status) {
        this.status = status;
    }

    public ERepayMode getRepayMode() {
        return repayMode;
    }

    public void setRepayMode(ERepayMode repayMode) {
        this.repayMode = repayMode;
    }

    public Boolean getCanPrepay() {
        return canPrepay;
    }

    public void setCanPrepay(Boolean canPrepay) {
        this.canPrepay = canPrepay;
    }

    public Long getPromoId() {
        return promoId;
    }

    public void setPromoId(Long promoId) {
        this.promoId = promoId;
    }

    public String getPromoName() {
        return promoName;
    }

    public void setPromoName(String promoName) {
        this.promoName = promoName;
    }

    public String getPromoDesc() {
        return promoDesc;
    }

    public void setPromoDesc(String promoDesc) {
        this.promoDesc = promoDesc;
    }

    public LoanBankDto getBank() {
        return bank;
    }

    public void setBank(LoanBankDto bank) {
        this.bank = bank;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public BigDecimal getSurplus() {
        return surplus;
    }

    public void setSurplus(BigDecimal surplus) {
        this.surplus = surplus;
    }

    public String getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(String paidDate) {
        this.paidDate = paidDate;
    }

    public BigDecimal getAuditAmt() {
        return auditAmt;
    }

    public void setAuditAmt(BigDecimal auditAmt) {
        this.auditAmt = auditAmt;
    }

    public BigDecimal getServiceFeeMonthRt() {
        return serviceFeeMonthRt;
    }

    public void setServiceFeeMonthRt(BigDecimal serviceFeeMonthRt) {
        this.serviceFeeMonthRt = serviceFeeMonthRt;
    }

    public BigDecimal getIntrRt() {
        return intrRt;
    }

    public void setIntrRt(BigDecimal intrRt) {
        this.intrRt = intrRt;
    }

    public BigDecimal getAuditServiceFeeMonthRt() {
        return auditServiceFeeMonthRt;
    }

    public void setAuditServiceFeeMonthRt(BigDecimal auditServiceFeeMonthRt) {
        this.auditServiceFeeMonthRt = auditServiceFeeMonthRt;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
