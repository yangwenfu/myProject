package com.xinyunlian.jinfu.payCode.dto;

import com.xinyunlian.jinfu.payCode.enums.PayCodeBalanceType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by carrot on 2017/8/28.
 */
public class PayCodeBalanceLogDto implements Serializable {

    private static final long serialVersionUID = -1L;

    private Long id;
    private String payCodeNo;
    private String mobile;
    private PayCodeBalanceType type;
    private BigDecimal amount;
    private Date lastMntTs;
    private String serialNumber;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public PayCodeBalanceType getType() {
        return type;
    }

    public void setType(PayCodeBalanceType type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getLastMntTs() {
        return lastMntTs;
    }

    public void setLastMntTs(Date lastMntTs) {
        this.lastMntTs = lastMntTs;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
