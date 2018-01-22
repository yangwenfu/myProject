package com.xinyunlian.jinfu.report.dealer.dto;

import com.xinyunlian.jinfu.report.dealer.enums.ESource;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by bright on 2016/11/29.
 */
public class DealerStoreInfDto implements Serializable{
    private String storeName;
    private Date createTs;
    private String province;
    private String city;
    private String area;
    private String street;
    private String fullAddress;
    private ESource source;
    private String name;

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
}
