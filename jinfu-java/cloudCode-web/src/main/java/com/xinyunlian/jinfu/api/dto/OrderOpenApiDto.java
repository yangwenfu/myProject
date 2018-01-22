package com.xinyunlian.jinfu.api.dto;

import com.xinyunlian.jinfu.api.validate.dto.OpenApiBaseDto;

/**
 * Created by menglei on 2016年11月21日.
 */
public class OrderOpenApiDto extends OpenApiBaseDto {

    private static final long serialVersionUID = -1L;

    private String mobile;

    private String amount;

    private String couponAmount;

    private String coupon;

    private String storeId;

    private String verifyCode;

    private String orderNo;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCoupon() {
        return coupon;
    }

    public void setCoupon(String coupon) {
        this.coupon = coupon;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(String couponAmount) {
        this.couponAmount = couponAmount;
    }
}
