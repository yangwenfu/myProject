package com.xinyunlian.jinfu.payCode.dto;

import com.xinyunlian.jinfu.payCode.enums.PayCodeStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by carrot on 2017/8/28.
 */
public class PayCodeDto implements Serializable {

    private static final long serialVersionUID = -1L;

    private String payCodeNo;
    private String mobile;
    private BigDecimal balance;
    private PayCodeStatus status;
    private Date createTs;
    private String payCodeUrl;

    /**
     * 消费金额总计
     */
    private BigDecimal totalPay;

    public String getPayCodeNo() {
        return payCodeNo;
    }

    public void setPayCodeNo(String payCodeNo) {
        this.payCodeNo = payCodeNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public PayCodeStatus getStatus() {
        return status;
    }

    public void setStatus(PayCodeStatus status) {
        this.status = status;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public BigDecimal getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(BigDecimal totalPay) {
        this.totalPay = totalPay;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPayCodeUrl() {
        return payCodeUrl;
    }

    public void setPayCodeUrl(String payCodeUrl) {
        this.payCodeUrl = payCodeUrl;
    }
}
