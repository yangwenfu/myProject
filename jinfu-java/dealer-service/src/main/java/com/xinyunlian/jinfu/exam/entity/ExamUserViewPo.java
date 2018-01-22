package com.xinyunlian.jinfu.exam.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.exam.enums.EExamStatus;
import com.xinyunlian.jinfu.exam.enums.EExamType;
import com.xinyunlian.jinfu.exam.enums.EExamUserStatus;
import com.xinyunlian.jinfu.exam.enums.converter.EExamStatusConverter;
import com.xinyunlian.jinfu.exam.enums.converter.EExamTypeConverter;
import com.xinyunlian.jinfu.exam.enums.converter.EExamUserStatusConverter;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by menglei on 2017年05月02日.
 */
@Entity
@Table(name = "exam_user_view")
public class ExamUserViewPo extends BaseMaintainablePo {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "DEALER_ID")
    private String dealerId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "EXAM_ID")
    private Long examId;

    @Column(name = "SCORE")
    private Integer score;

    @Column(name = "STATUS")
    @Convert(converter = EExamUserStatusConverter.class)
    private EExamUserStatus status;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TYPE")
    @Convert(converter = EExamTypeConverter.class)
    private EExamType type;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "DEALER_NAME")
    private String dealerName;

    @Column(name = "PROVINCE_ID")
    private String provinceId;

    @Column(name = "CITY_ID")
    private String cityId;

    @Column(name = "AREA_ID")
    private String areaId;

    @Column(name = "STREET_ID")
    private String streetId;

    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "CITY")
    private String city;

    @Column(name = "AREA")
    private String area;

    @Column(name = "STREET")
    private String street;

    @Column(name = "CREATE_DEALER_OPID")
    private String createDealerOpId;

    @Column(name = "CREATE_DEALER_OPNAME")
    private String createDealerOpName;

    @Column(name = "BELONG_ID")
    private String belongId;

    @Column(name = "BELONG_NAME")
    private String belongName;

    @Column(name = "START_TIME")
    private Date startTime;

    @Column(name = "END_TIME")
    private Date endTime;

    @Column(name = "EXAM_STATUS")
    @Convert(converter = EExamStatusConverter.class)
    private EExamStatus examStatus;

    @Column(name = "EXAM_CREATE_TS")
    private Date examCreateTs;

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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public EExamStatus getExamStatus() {
        return examStatus;
    }

    public void setExamStatus(EExamStatus examStatus) {
        this.examStatus = examStatus;
    }

    public Date getExamCreateTs() {
        return examCreateTs;
    }

    public void setExamCreateTs(Date examCreateTs) {
        this.examCreateTs = examCreateTs;
    }
}
