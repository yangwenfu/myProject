package com.xinyunlian.jinfu.dealer.order.dto;

import com.xinyunlian.jinfu.report.dealer.enums.EDealType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by menglei on 2017年06月07日.
 */
public class DealerOrderReportDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;
    private String orderNo;
    private String productName;
    private String storeName;
    private String userName;
    private BigDecimal orderAmt;
    private EDealType dealType;
    private Date orderDate;
    private String operatorName;
    private String dealerName;
    private String province;
    private String city;
    private String area;
    private String belongName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(BigDecimal orderAmt) {
        this.orderAmt = orderAmt;
    }

    public EDealType getDealType() {
        return dealType;
    }

    public void setDealType(EDealType dealType) {
        this.dealType = dealType;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBelongName() {
        return belongName;
    }

    public void setBelongName(String belongName) {
        this.belongName = belongName;
    }
}
