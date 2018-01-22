package com.xinyunlian.jinfu.spider.dto.resp;

import java.io.Serializable;

/**
 * Created by menglei on 2016年11月08日.
 */
public class RiskUserOrderDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long id;
    private long riskUserId;
    private String orderNo;//订单号
    private String orderTime;//订单日期
    private String amount;//数量
    private String totalPrice;//总价
    private String unitPrice;//单价

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRiskUserId() {
        return riskUserId;
    }

    public void setRiskUserId(long riskUserId) {
        this.riskUserId = riskUserId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }
}
