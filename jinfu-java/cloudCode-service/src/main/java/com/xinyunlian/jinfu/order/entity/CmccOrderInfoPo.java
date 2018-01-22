package com.xinyunlian.jinfu.order.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;
import com.xinyunlian.jinfu.order.enums.ECmccOrderSource;
import com.xinyunlian.jinfu.order.enums.ECmccOrderPayStatus;
import com.xinyunlian.jinfu.order.enums.ECmccOrderTradeStatus;
import com.xinyunlian.jinfu.order.enums.converters.ECmccOrderSourceConverter;
import com.xinyunlian.jinfu.order.enums.converters.ECmccOrderPayStatusConverter;
import com.xinyunlian.jinfu.order.enums.converters.ECmccOrderTradeStatusConverter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by menglei on 2016年11月20日.
 */
@Entity
@Table(name = "cmcc_order_info")
@EntityListeners(IdInjectionEntityListener.class)
public class CmccOrderInfoPo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CMCC_ORDER_NO")
    private String cmccOrderNo;

    @Column(name = "OUT_TRADE_NO")
    private String outTradeNo;

    @Column(name = "CMCC_TRADE_NO")
    private String cmccTradeNo;

    @Column(name = "STORE_ID")
    private Long storeId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "COUPON_AMOUNT")
    private BigDecimal couponAmount;

    @Column(name = "COUPON")
    private BigDecimal coupon;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "PAY_STATUS")
    @Convert(converter = ECmccOrderPayStatusConverter.class)
    private ECmccOrderPayStatus payStatus;

    @Column(name = "TRADE_STATUS")
    @Convert(converter = ECmccOrderTradeStatusConverter.class)
    private ECmccOrderTradeStatus tradeStatus;

    @Column(name = "SOURCE")
    @Convert(converter = ECmccOrderSourceConverter.class)
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

    public ECmccOrderSource getSource() {
        return source;
    }

    public void setSource(ECmccOrderSource source) {
        this.source = source;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public ECmccOrderPayStatus getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(ECmccOrderPayStatus payStatus) {
        this.payStatus = payStatus;
    }

    public String getCmccTradeNo() {
        return cmccTradeNo;
    }

    public void setCmccTradeNo(String cmccTradeNo) {
        this.cmccTradeNo = cmccTradeNo;
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
