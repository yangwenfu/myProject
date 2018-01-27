package com.xinyunlian.jinfu.user.dto;

import com.xinyunlian.jinfu.user.enums.ELinkDateType;
import com.xinyunlian.jinfu.user.enums.ELinkManOrder;
import com.xinyunlian.jinfu.user.enums.ERelationship;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户联系人Entity
 *
 * @author KimLL
 */

public class UserLinkmanDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long linkmanId;

    private String userId = "";
    //姓名
    private String name = "";

    private String phone;

    private ELinkManOrder orders;

    //关系
    private ERelationship relationship;
    //手机号
    private String mobile = "";

    private String province = "";

    private String city = "";

    private String area = "";

    private String street = "";

    //所在地址
    private String address = "";

    private ELinkDateType dateType = ELinkDateType.OWN;

    private Date createTs;

    public void setLinkmanId(Long linkmanId) {
        this.linkmanId = linkmanId;
    }

    public Long getLinkmanId() {
        return linkmanId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ELinkManOrder getOrders() {
        return orders;
    }

    public void setOrders(ELinkManOrder orders) {
        this.orders = orders;
    }

    public ERelationship getRelationship() {
        return relationship;
    }

    public void setRelationship(ERelationship relationship) {
        this.relationship = relationship;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
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

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public ELinkDateType getDateType() {
        return dateType;
    }

    public void setDateType(ELinkDateType dateType) {
        this.dateType = dateType;
    }
}


