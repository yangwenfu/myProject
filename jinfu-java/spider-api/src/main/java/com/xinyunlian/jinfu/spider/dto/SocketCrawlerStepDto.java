package com.xinyunlian.jinfu.spider.dto;

import java.io.Serializable;

/**
 * Created by carrot on 2017/8/7.
 */
public class SocketCrawlerStepDto implements Serializable {

    private Long id;

    private Long areaId;

    private Long provinceId;

    private Long cityId;

    private String province;

    private String city;

    private String area;

    private String url;

    private SocketConfigDto config;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SocketConfigDto getConfig() {
        return config;
    }

    public void setConfig(SocketConfigDto config) {
        this.config = config;
    }
}
