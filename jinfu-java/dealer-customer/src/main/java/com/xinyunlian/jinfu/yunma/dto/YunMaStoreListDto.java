package com.xinyunlian.jinfu.yunma.dto;

import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Date;


/**
 * Created by menglei on 2014/4/12.
 */
public class YunMaStoreListDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long storeId;

    private String storeName = "";

    private String userId = "";

    private String province = "";

    private String city = "";

    private String area = "";

    private String street = "";

    private Integer type = 0;// 0=可绑 1=已绑 2=不在业务范围

    private Date createTs;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return StringUtils.isNotEmpty(storeName) ? storeName : "";
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getUserId() {
        return StringUtils.isNotEmpty(userId) ? userId : "";
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProvince() {
        return StringUtils.isNotEmpty(province) ? province : "";
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return StringUtils.isNotEmpty(city) ? city : "";
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return StringUtils.isNotEmpty(area) ? area : "";
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreet() {
        return StringUtils.isNotEmpty(street) ? street : "";
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
