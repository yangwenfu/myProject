package com.xinyunlian.jinfu.yunma.dto;

import java.io.Serializable;

/**
 * Created by King on 2017-1-11.
 */
public class YmAreaDetailDto implements Serializable{
    private static final long serialVersionUID = -8237571221893129288L;

    private Long prodAreaId;
    private Long provinceId;
    private Long cityId;
    private Long countyId;
    private Long streetId;

    public Long getProdAreaId() {
        return prodAreaId;
    }

    public void setProdAreaId(Long prodAreaId) {
        this.prodAreaId = prodAreaId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getCountyId() {
        return countyId;
    }

    public void setCountyId(Long countyId) {
        this.countyId = countyId;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getStreetId() {
        return streetId;
    }

    public void setStreetId(Long streetId) {
        this.streetId = streetId;
    }
}
