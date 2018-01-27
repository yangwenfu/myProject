package com.xinyunlian.jinfu.loan.dto.repay;

import com.xinyunlian.jinfu.loan.enums.ERepayStatus;
import com.xinyunlian.jinfu.loan.enums.ETransMode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Willwang on 2017/5/9.
 */
public class RepayHistoryItemDto implements Serializable{

    /**
     * 还款编号
     */
    private String repayId;

    /**
     * 支付方式
     */
    private ETransMode transMode;

    /**
     * 还款时间
     */
    private String repayDate;

    /**
     * 金额
     */
    private BigDecimal amt;

    /**
     * 还款状态
     */
    private ERepayStatus repayStatus;

    public String getRepayId() {
        return repayId;
    }

    public void setRepayId(String repayId) {
        this.repayId = repayId;
    }

    public ETransMode getTransMode() {
        return transMode;
    }

    public void setTransMode(ETransMode transMode) {
        this.transMode = transMode;
    }

    public String getRepayDate() {
        return repayDate;
    }

    public void setRepayDate(String repayDate) {
        this.repayDate = repayDate;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public ERepayStatus getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(ERepayStatus repayStatus) {
        this.repayStatus = repayStatus;
    }
}
