package com.xinyunlian.jinfu.report.virtual.entity;

import com.xinyunlian.jinfu.report.dealer.enums.ESource;
import com.xinyunlian.jinfu.report.dealer.enums.converter.ESourceEnumConverter;
import com.xinyunlian.jinfu.report.virtual.enums.ETakeType;
import com.xinyunlian.jinfu.report.virtual.enums.ETakeTypeConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 虚拟烟草证Entity
 *
 * @author jll
 */
@Entity
@Table(name = "VIRTUAL_TOBACCO")
public class VirtualTboSearchPo implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "TOBACCO_CERTIFICATE_NO")
    private String tobaccoCertificateNo;

    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "CITY")
    private String city;

    @Column(name = "AREA")
    private String area;

    @Column(name = "STREET")
    private String street;

    @Column(name = "TAKE_TYPE")
    @Convert(converter = ETakeTypeConverter.class)
    private ETakeType takeType;

    @Column(name = "TAKE_TIME")
    private Date takeTime;

    @Column(name = "SOURCE")
    @Convert(converter = ESourceEnumConverter.class)
    private ESource source;

    @Column(name = "ASSIGN_PERSON")
    private String assignPerson;

    @Column(name = "REMARK")
    private String remark;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTobaccoCertificateNo() {
        return tobaccoCertificateNo;
    }

    public void setTobaccoCertificateNo(String tobaccoCertificateNo) {
        this.tobaccoCertificateNo = tobaccoCertificateNo;
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

    public ETakeType getTakeType() {
        return takeType;
    }

    public void setTakeType(ETakeType takeType) {
        this.takeType = takeType;
    }

    public Date getTakeTime() {
        return takeTime;
    }

    public void setTakeTime(Date takeTime) {
        this.takeTime = takeTime;
    }

    public String getAssignPerson() {
        return assignPerson;
    }

    public void setAssignPerson(String assignPerson) {
        this.assignPerson = assignPerson;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ESource getSource() {
        return source;
    }

    public void setSource(ESource source) {
        this.source = source;
    }
}


