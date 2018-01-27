package com.xinyunlian.jinfu.report.dealer.entity;

import com.xinyunlian.jinfu.report.dealer.enums.EDealerType;
import com.xinyunlian.jinfu.report.dealer.enums.converter.EDealerTypeEnumConverter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by bright on 2016/11/30.
 */
@Entity
@Table(name = "dealer_user")
public class DealerUserReportPo {
    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "dealer_name")
    private String dealerName;

    @Column(name = "level_id")
    private String levelId;

    @Column(name = "province_id")
    private String provinceId;

    @Column(name = "city_id")
    private String cityId;

    @Column(name = "area_id")
    private String areaId;

    @Column(name = "street_id")
    private String streetId;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "create_ts")
    private Date createTs;

    @Column(name = "prod_province_id")
    private String prodProvinceId;

    @Column(name = "prod_city_id")
    private String prodCityId;

    @Column(name = "prod_area_id")
    private String prodAreaId;

    @Column(name = "prod_street_id")
    private String prodStreetId;

    @Column(name = "dealer_create_ts")
    private Date dealerCreateTs;

    @Column(name = "type")
    @Convert(converter = EDealerTypeEnumConverter.class)
    private EDealerType type;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getLevelId() {
        return levelId;
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getStreetId() {
        return streetId;
    }

    public void setStreetId(String streetId) {
        this.streetId = streetId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public String getProdProvinceId() {
        return prodProvinceId;
    }

    public void setProdProvinceId(String prodProvinceId) {
        this.prodProvinceId = prodProvinceId;
    }

    public String getProdCityId() {
        return prodCityId;
    }

    public void setProdCityId(String prodCityId) {
        this.prodCityId = prodCityId;
    }

    public String getProdAreaId() {
        return prodAreaId;
    }

    public void setProdAreaId(String prodAreaId) {
        this.prodAreaId = prodAreaId;
    }

    public String getProdStreetId() {
        return prodStreetId;
    }

    public void setProdStreetId(String prodStreetId) {
        this.prodStreetId = prodStreetId;
    }

    public Date getDealerCreateTs() {
        return dealerCreateTs;
    }

    public void setDealerCreateTs(Date dealerCreateTs) {
        this.dealerCreateTs = dealerCreateTs;
    }

    public EDealerType getType() {
        return type;
    }

    public void setType(EDealerType type) {
        this.type = type;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
