package com.xinyunlian.jinfu.spider.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * Created by menglei on 2016年11月07日.
 */
@Entity
@Table(name = "risk_user_order")
public class RiskUserOrderPo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "RISK_USER_ID")
    private long riskUserId;

    @Column(name = "ORDER_NO")
    private String orderNo;//订单号

    @Column(name = "ORDER_TIME")
    private String orderTime;//订单日期

    @Column(name = "AMOUNT")
    private String amount;//数量

    @Column(name = "TOTAL_PRICE")
    private String totalPrice;//总价

    @Column(name = "UNIT_PRICE")
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
