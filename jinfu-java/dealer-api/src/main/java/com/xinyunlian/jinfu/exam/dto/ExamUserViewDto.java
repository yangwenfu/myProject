package com.xinyunlian.jinfu.exam.dto;

import com.xinyunlian.jinfu.exam.enums.EExamType;
import com.xinyunlian.jinfu.exam.enums.EExamUserStatus;

import java.io.Serializable;

/**
 * Created by menglei on 2017年05月02日.
 */
public class ExamUserViewDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String dealerId;

    private String userId;

    private Long examId;

    private Integer score;

    private EExamUserStatus status;

    private String name;

    private EExamType type;

    private String userName;

    private String mobile;

    private String dealerName;

    private String provinceId;

    private String cityId;

    private String areaId;

    private String streetId;

    private String province;

    private String city;

    private String area;

    private String street;

    private String createDealerOpId;

    private String createDealerOpName;

    private String belongId;

    private String belongName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public EExamUserStatus getStatus() {
        return status;
    }

    public void setStatus(EExamUserStatus status) {
        this.status = status;
    }

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

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getStreetId() {
        return streetId;
    }

    public void setStreetId(String streetId) {
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

    public String getCreateDealerOpId() {
        return createDealerOpId;
    }

    public void setCreateDealerOpId(String createDealerOpId) {
        this.createDealerOpId = createDealerOpId;
    }

    public String getCreateDealerOpName() {
        return createDealerOpName;
    }

    public void setCreateDealerOpName(String createDealerOpName) {
        this.createDealerOpName = createDealerOpName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EExamType getType() {
        return type;
    }

    public void setType(EExamType type) {
        this.type = type;
    }

    public String getBelongId() {
        return belongId;
    }

    public void setBelongId(String belongId) {
        this.belongId = belongId;
    }

    public String getBelongName() {
        return belongName;
    }

    public void setBelongName(String belongName) {
        this.belongName = belongName;
    }
}
