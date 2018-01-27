package com.xinyunlian.jinfu.report.dealer.dto;

import com.xinyunlian.jinfu.report.dealer.enums.ESource;

import java.io.Serializable;

/**
 * Created by bright on 2016/11/29.
 */
public class DealerStoreSearchDto implements Serializable {
    private String storeName;

    private String province;

    private String city;

    private String area;

    private String street;

    private String dealerName;

    private ESource source;

    private String CreateStartDate;

    private String CreateEndDate;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public ESource getSource() {
        return source;
    }

    public void setSource(ESource source) {
        this.source = source;
    }

    public String getCreateStartDate() {
        return CreateStartDate;
    }

    public void setCreateStartDate(String createStartDate) {
        CreateStartDate = createStartDate;
    }

    public String getCreateEndDate() {
        return CreateEndDate;
    }

    public void setCreateEndDate(String createEndDate) {
        CreateEndDate = createEndDate;
    }
}
