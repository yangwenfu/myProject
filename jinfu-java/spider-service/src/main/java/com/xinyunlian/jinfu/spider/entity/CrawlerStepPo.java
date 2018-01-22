package com.xinyunlian.jinfu.spider.entity;

import com.xinyunlian.jinfu.spider.enums.EDataType;
import com.xinyunlian.jinfu.spider.enums.EHttpMethod;
import com.xinyunlian.jinfu.spider.enums.EProcessor;
import com.xinyunlian.jinfu.spider.enums.converter.EDataTypeConverter;
import com.xinyunlian.jinfu.spider.enums.converter.EHttpMethodConverter;
import com.xinyunlian.jinfu.spider.enums.converter.EProcessorConverter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by bright on 2016/12/8.
 */
@Entity
@Table(name = "crawler_step")
public class CrawlerStepPo implements Serializable {
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


    @Column(name = "DATA_TYPE")
    @Convert(converter = EDataTypeConverter.class)
    private EDataType dataType;

    @Column(name = "method")
    @Convert(converter = EHttpMethodConverter.class)
    private EHttpMethod method;

    @Column(name = "ORDER")
    private Long order;

    @Column(name = "url")
    private String url;

    @Column(name = "params")
    private String params;

    @Column(name = "PROCESSOR")
    @Convert(converter = EProcessorConverter.class)
    private EProcessor processor;

    @Column(name = "processor_config")
    private String processorConfig;

    /* Readable Properties start*/
    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "CITY")
    private String city;

    @Column(name = "AREA")
    private String area;

    @Column(name = "NAME")
    private String name;
    /* Readable Properties start*/

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

    public EDataType getDataType() {
        return dataType;
    }

    public void setDataType(EDataType dataType) {
        this.dataType = dataType;
    }

    public EHttpMethod getMethod() {
        return method;
    }

    public void setMethod(EHttpMethod method) {
        this.method = method;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public EProcessor getProcessor() {
        return processor;
    }

    public void setProcessor(EProcessor processor) {
        this.processor = processor;
    }

    public String getProcessorConfig() {
        return processorConfig;
    }

    public void setProcessorConfig(String processorConfig) {
        this.processorConfig = processorConfig;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
