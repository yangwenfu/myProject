package com.xinyunlian.jinfu.risk.dto;

import com.xinyunlian.jinfu.risk.enums.ERiskOrderType;

import java.io.Serializable;

/**
 * @author willwang
 */
public class RiskUserOrderDto implements Serializable {

    private String userId;

    private String orderNo;

    private String orderTime;

    private String orderCount;

    private String orderAmount;

    private String orderUnit;

    private ERiskOrderType riskOrderType;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public ERiskOrderType getRiskOrderType() {
        return riskOrderType;
    }

    public void setRiskOrderType(ERiskOrderType riskOrderType) {
        this.riskOrderType = riskOrderType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(String orderCount) {
        this.orderCount = orderCount;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderUnit() {
        return orderUnit;
    }

    public void setOrderUnit(String orderUnit) {
        this.orderUnit = orderUnit;
    }
}
