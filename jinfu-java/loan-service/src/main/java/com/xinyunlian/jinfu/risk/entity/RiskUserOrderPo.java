package com.xinyunlian.jinfu.risk.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.loan.enums.converter.ETermTypeEnumConverter;
import com.xinyunlian.jinfu.risk.enums.ERiskOrderType;
import com.xinyunlian.jinfu.risk.enums.converter.ERiskOrderTypeEnumConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "RISK_USER_ORDER")
public class RiskUserOrderPo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "ORDER_NO")
    private String orderNo;

    @Column(name = "ORDER_TIME")
    private String orderTime;

    @Column(name = "ORDER_COUNT")
    private Integer orderCount;

    @Column(name = "ORDER_AMOUNT")
    private String orderAmount;

    @Column(name = "ORDER_UNIT")
    private String orderUnit;

    @Convert(converter = ERiskOrderTypeEnumConverter.class)
    @Column(name = "ORDER_TYPE")
    private ERiskOrderType riskOrderType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
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
