package com.xinyunlian.jinfu.order.dto;

import com.xinyunlian.jinfu.order.enums.ECmccOrderSource;
import com.xinyunlian.jinfu.order.enums.ECmccOrderPayStatus;
import com.xinyunlian.jinfu.order.enums.ECmccOrderTradeStatus;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by menglei on 2016年11月20日.
 */
public class CmccOrderInfoDto implements Serializable {
    private static final long serialVersionUID = -6375635388364584073L;

    private String cmccOrderNo;

    private String outTradeNo;

    private String cmccTradeNo;

    private Long storeId;

    private Long userId;

    private BigDecimal amount;

    private BigDecimal couponAmount;

    private BigDecimal coupon;

    private String mobile;

    private ECmccOrderPayStatus payStatus;

    private ECmccOrderTradeStatus tradeStatus;

    private ECmccOrderSource source;

    public String getCmccOrderNo() {
        return cmccOrderNo;
    }

    public void setCmccOrderNo(String cmccOrderNo) {
        this.cmccOrderNo = cmccOrderNo;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ECmccOrderSource getSource() {
        return source;
    }

    public void setSource(ECmccOrderSource source) {
        this.source = source;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getCmccTradeNo() {
        return cmccTradeNo;
    }

    public void setCmccTradeNo(String cmccTradeNo) {
        this.cmccTradeNo = cmccTradeNo;
    }

    public ECmccOrderPayStatus getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(ECmccOrderPayStatus payStatus) {
        this.payStatus = payStatus;
    }

    public ECmccOrderTradeStatus getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(ECmccOrderTradeStatus tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getCoupon() {
        return coupon;
    }

    public void setCoupon(BigDecimal coupon) {
        this.coupon = coupon;
    }

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
