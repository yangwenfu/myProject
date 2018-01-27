package com.xinyunlian.jinfu.promo.dto;

import java.io.Serializable;

/**
 * Created by bright on 2016/11/24.
 */
public class PromotionAreaDto implements Serializable {
    private Long id;

    private Long provinceId;

    private Long cityId;

    private Long countyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
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
}
