package com.xinyunlian.jinfu.promo.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by King on 2017/5/26.
 */
public class CouponGiftDto implements Serializable{
    private static final long serialVersionUID = 1L;
    private String userId;
    //优惠券code
    private String couponCode;
    //优惠券来源
    private String source;
    //优惠券流水号
    private String tradeNo;
    //优惠券发放时间
    private Date fromDateTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Date getFromDateTime() {
        return fromDateTime;
    }

    public void setFromDateTime(Date fromDateTime) {
        this.fromDateTime = fromDateTime;
    }
}
