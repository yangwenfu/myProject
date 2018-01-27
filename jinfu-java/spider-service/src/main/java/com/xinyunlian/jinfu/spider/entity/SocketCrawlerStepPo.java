package com.xinyunlian.jinfu.spider.entity;

import com.xinyunlian.jinfu.spider.dto.SocketConfigDto;
import com.xinyunlian.jinfu.spider.enums.converter.SocketConfigConverter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by carrot on 2017/8/7.
 */
@Entity
@Table(name = "socket_crawler_step")
public class SocketCrawlerStepPo implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    /* Region Properties start */
    @Column(name = "AREA_ID")
    private Long areaId;

    @Column(name = "PROVINCE_ID")
    private Long provinceId;

    @Column(name = "CITY_ID")
    private Long cityId;
    /* Region Properties end */

    /* Readable Properties start*/
    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "CITY")
    private String city;

    @Column(name = "AREA")
    private String area;

    /* Readable Properties start*/

    @Column(name = "url")
    private String url;

    @Column(name = "CONFIG")
    @Convert(converter = SocketConfigConverter.class)
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
