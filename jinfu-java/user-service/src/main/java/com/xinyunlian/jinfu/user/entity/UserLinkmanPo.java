package com.xinyunlian.jinfu.user.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.user.enums.ELinkDateType;
import com.xinyunlian.jinfu.user.enums.ELinkManOrder;
import com.xinyunlian.jinfu.user.enums.ERelationship;
import com.xinyunlian.jinfu.user.enums.converter.ELinkDateTypeConverter;
import com.xinyunlian.jinfu.user.enums.converter.ELinkManOrderConverter;
import com.xinyunlian.jinfu.user.enums.converter.ERelationshipConverter;

import javax.persistence.*;

/**
 * 用户联系人Entity
 *
 * @author KimLL
 */
@Entity
@Table(name = "USER_LINKMAN")
public class UserLinkmanPo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "LINKMAN_ID")
    private long linkmanId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "ORDERS")
    @Convert(converter = ELinkManOrderConverter.class)
    private ELinkManOrder orders;

    @Column(name = "RELATIONSHIP")
    @Convert(converter = ERelationshipConverter.class)
    private ERelationship relationship;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "CITY")
    private String city;

    @Column(name = "AREA")
    private String area;

    @Column(name = "STREET")
    private String street;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "DATE_TYPE")
    @Convert(converter = ELinkDateTypeConverter.class)
    private ELinkDateType dateType;

    public void setLinkmanId(long linkmanId) {
        this.linkmanId = linkmanId;
    }

    public long getLinkmanId() {
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

    public ERelationship getRelationship() {
        return relationship;
    }

    public void setRelationship(ERelationship relationship) {
        this.relationship = relationship;
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

    public ELinkDateType getDateType() {
        return dateType;
    }

    public void setDateType(ELinkDateType dateType) {
        this.dateType = dateType;
    }
}


