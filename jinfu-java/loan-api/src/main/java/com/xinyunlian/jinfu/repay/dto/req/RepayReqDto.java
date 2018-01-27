package com.xinyunlian.jinfu.repay.dto.req;

import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.enums.ERepayType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Willwang on 2016/11/25.
 */
public class RepayReqDto implements Serializable {

    private String repayId;

    private String loanId;

    private BigDecimal amt;

    private ERepayType repayType;

    /**
     * 当repayType为PERIOD时，period必定存在
     */
    private Integer period;

    /**
     * 手动代扣时可以强制指定代扣日
     */
    private Date forceDate;

    /**
     * 优惠券编号
     */
    private List<Long> couponIds;

    /**
     * 期望还款金额,做页面过期判定
     */
    private BigDecimal expected;

    /**
     * 存管还款时的验证码
     */
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getExpected() {
        return expected;
    }

    public void setExpected(BigDecimal expected) {
        this.expected = expected;
    }

    public String getRepayId() {
        return repayId;
    }

    public void setRepayId(String repayId) {
        this.repayId = repayId;
    }

    public List<Long> getCouponIds() {
        return couponIds;
    }

    public void setCouponIds(List<Long> couponIds) {
        this.couponIds = couponIds;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public ERepayType getRepayType() {
        return repayType;
    }

    public void setRepayType(ERepayType repayType) {
        this.repayType = repayType;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Date getForceDate() {
        return forceDate;
    }

    public void setForceDate(Date forceDate) {
        this.forceDate = forceDate;
    }

    @Override
    public String toString() {
        return "RepayReqDto{" +
                "loanId='" + loanId + '\'' +
                ", amt=" + amt +
                ", repayType=" + repayType +
                ", period=" + period +
                ", forceDate=" + forceDate +
                ", couponIds=" + couponIds +
                '}';
    }
}
