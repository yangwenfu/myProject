package com.xinyunlian.jinfu.report.dealer.entity;

import com.xinyunlian.jinfu.report.dealer.enums.EDealType;
import com.xinyunlian.jinfu.report.dealer.enums.converter.EDealTypeEnumConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by bright on 2016/11/29.
 */
@Entity
@Table(name = "dealer_order")
public class DealerOrderPo implements Serializable {
    @Column(name = "user_id")
    private String userId;

    @Id
    @Column(name = "order_no")
    private String orderNo;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "order_amt")
    private BigDecimal orderAmt;

    @Column(name = "deal_type")
    @Convert(converter = EDealTypeEnumConverter.class)
    private EDealType dealType;

    @Column(name = "order_date")
    private Date orderDate;

    @Column(name = "operator_name")
    private String operatorName;

    @Column(name = "dealer_name")
    private String dealerName;

    @Column(name = "province_id")
    private Long provinceId;

    @Column(name = "province")
    private String province;

    @Column(name = "city_id")
    private Long cityId;

    @Column(name = "city")
    private String city;

    @Column(name = "area_id")
    private Long areaId;

    @Column(name = "area")
    private String area;

    @Column(name = "street_id")
    private Long streetId;

    @Column(name = "street")
    private String street;

    @Column(name = "dealer_province_id")
    private String dealerProvinceId;

    @Column(name = "dealer_city_id")
    private String dealerCityId;

    @Column(name = "dealer_area_id")
    private String dealerAreaId;

    @Column(name = "dealer_street_id")
    private String dealerStreetId;

    @Column(name = "BELONG_ID")
    private String belongId;

    @Column(name = "BELONG_NAME")
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

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Long getStreetId() {
        return streetId;
    }

    public void setStreetId(Long streetId) {
        this.streetId = streetId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getDealerProvinceId() {
        return dealerProvinceId;
    }

    public void setDealerProvinceId(String dealerProvinceId) {
        this.dealerProvinceId = dealerProvinceId;
    }

    public String getDealerCityId() {
        return dealerCityId;
    }

    public void setDealerCityId(String dealerCityId) {
        this.dealerCityId = dealerCityId;
    }

    public String getDealerAreaId() {
        return dealerAreaId;
    }

    public void setDealerAreaId(String dealerAreaId) {
        this.dealerAreaId = dealerAreaId;
    }

    public String getDealerStreetId() {
        return dealerStreetId;
    }

    public void setDealerStreetId(String dealerStreetId) {
        this.dealerStreetId = dealerStreetId;
    }

    public String getBelongId() {
        return belongId;
    }

    public void setBelongId(String belongId) {
        this.belongId = belongId;
    }

    public String getBelongName() {
        return belongName;
    }

    public void setBelongName(String belongName) {
        this.belongName = belongName;
    }
}
