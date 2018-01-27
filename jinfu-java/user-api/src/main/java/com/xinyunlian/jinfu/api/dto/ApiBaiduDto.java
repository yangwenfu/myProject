package com.xinyunlian.jinfu.api.dto;

import java.io.Serializable;

/**
 * 百度Entity
 *
 * @author menglei
 */

public class ApiBaiduDto implements Serializable {

    private static final long serialVersionUID = 1L;

    //省
    private String province;

    //市
    private String city;

    //区
    private String area;

    //国标码
    private String gbCode;

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

    public String getGbCode() {
        return gbCode;
    }

    public void setGbCode(String gbCode) {
        this.gbCode = gbCode;
    }
}


