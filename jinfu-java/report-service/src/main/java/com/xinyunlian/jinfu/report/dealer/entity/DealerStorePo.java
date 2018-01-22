package com.xinyunlian.jinfu.report.dealer.entity;

import com.xinyunlian.jinfu.report.dealer.enums.ESource;
import com.xinyunlian.jinfu.report.dealer.enums.converter.ESourceEnumConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by bright on 2016/11/29.
 */
@Entity
@Table(name = "dealer_store")
public class DealerStorePo implements Serializable{
    @Id
    @Column(name = "STORE_ID")
    private Long storeId;

    @Column(name = "STORE_NAME")
    private String storeName;

    @Column(name = "CREATE_TS")
    private Date createTs;

    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "CITY")
    private String city;

    @Column(name = "AREA")
    private String area;

    @Column(name = "STREET")
    private String street;

    @Column(name = "FULL_ADDRESS")
    private String fullAddress;

    @Column(name = "SOURCE")
    @Convert(converter = ESourceEnumConverter.class)
    private ESource source;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DEALER_NAME")
    private String dealerName;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public ESource getSource() {
        return source;
    }

    public void setSource(ESource source) {
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }
}
