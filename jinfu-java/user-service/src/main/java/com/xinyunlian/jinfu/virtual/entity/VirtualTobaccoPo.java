package com.xinyunlian.jinfu.virtual.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.virtual.enums.ETakeStatus;
import com.xinyunlian.jinfu.virtual.enums.ETakeStatusConverter;
import com.xinyunlian.jinfu.virtual.enums.ETakeType;
import com.xinyunlian.jinfu.virtual.enums.ETakeTypeConverter;

import javax.persistence.*;
import java.util.Date;

/**
 * 虚拟烟草证Entity
 *
 * @author jll
 */
@Entity
@Table(name = "VIRTUAL_TOBACCO")
public class VirtualTobaccoPo extends BaseMaintainablePo {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TOBACCO_CERTIFICATE_NO")
    private String tobaccoCertificateNo;

    @Column(name = "PROVINCE_ID")
    private Long provinceId;

    @Column(name = "CITY_ID")
    private Long cityId;

    @Column(name = "AREA_ID")
    private Long areaId;

    @Column(name = "STREET_ID")
    private Long streetId;

    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "CITY")
    private String city;

    @Column(name = "AREA")
    private String area;

    @Column(name = "STREET")
    private String street;

    @Column(name = "AREA_CODE")
    private String areaCode;

    @Column(name = "PIN_CODE")
    private String pinCode;

    @Column(name = "SERIAL")
    private Long serial;

    @Column(name = "TREE_PATH")
    private String treePath;

    @Column(name = "STATUS")
    @Convert(converter = ETakeStatusConverter.class)
    private ETakeStatus status;

    @Column(name = "TAKE_TYPE")
    @Convert(converter = ETakeTypeConverter.class)
    private ETakeType takeType;

    @Column(name = "TAKE_TIME")
    private Date takeTime;

    @Column(name = "ASSIGN_PERSON")
    private String assignPerson;

    @Column(name = "REMARK")
    private String remark;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTobaccoCertificateNo() {
        return tobaccoCertificateNo;
    }

    public void setTobaccoCertificateNo(String tobaccoCertificateNo) {
        this.tobaccoCertificateNo = tobaccoCertificateNo;
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

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Long getStreetId() {
        return streetId;
    }

    public void setStreetId(Long streetId) {
        this.streetId = streetId;
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

    public String getTreePath() {
        return treePath;
    }

    public void setTreePath(String treePath) {
        this.treePath = treePath;
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

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public Long getSerial() {
        return serial;
    }

    public void setSerial(Long serial) {
        this.serial = serial;
    }

    public ETakeStatus getStatus() {
        return status;
    }

    public void setStatus(ETakeStatus status) {
        this.status = status;
    }
}


