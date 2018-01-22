package com.xinyunlian.jinfu.report.dealer.entity;

import com.xinyunlian.jinfu.report.dealer.enums.EDealerType;
import com.xinyunlian.jinfu.report.dealer.enums.converter.EDealerTypeEnumConverter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by bright on 2016/11/29.
 */
@Entity
@Table(name = "dealer")
public class DealerPo {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "dealer_id")
    private Long dealerId;

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

    @Column(name = "region")
    private String region;

    @Column(name = "create_ts")
    private Date createTs;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "product")
    private String product;

    @Column(name = "type")
    @Convert(converter = EDealerTypeEnumConverter.class)
    private EDealerType type;

    @Column(name = "level_id")
    private Long levelId;

    @Column(name = "level_name")
    private String levelName;

    @Column(name = "prod_province_id")
    private Long prodProvinceId;

    @Column(name = "prod_city_id")
    private Long prodCityId;

    @Column(name = "prod_area_id")
    private Long prodAreaId;

    @Column(name = "prod_street_id")
    private Long prodStreetId;

    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public EDealerType getType() {
        return type;
    }

    public void setType(EDealerType type) {
        this.type = type;
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Long getProdProvinceId() {
        return prodProvinceId;
    }

    public void setProdProvinceId(Long prodProvinceId) {
        this.prodProvinceId = prodProvinceId;
    }

    public Long getProdCityId() {
        return prodCityId;
    }

    public void setProdCityId(Long prodCityId) {
        this.prodCityId = prodCityId;
    }

    public Long getProdAreaId() {
        return prodAreaId;
    }

    public void setProdAreaId(Long prodAreaId) {
        this.prodAreaId = prodAreaId;
    }

    public Long getProdStreetId() {
        return prodStreetId;
    }

    public void setProdStreetId(Long prodStreetId) {
        this.prodStreetId = prodStreetId;
    }
}
