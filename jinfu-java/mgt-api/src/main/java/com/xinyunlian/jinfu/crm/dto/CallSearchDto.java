package com.xinyunlian.jinfu.crm.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;
import com.xinyunlian.jinfu.crm.enums.ECallStatus;

import java.util.Date;

/**
 * 客户通话记录Entity
 *
 * @author jll
 */
public class CallSearchDto extends PagingDto<CrmCallDto> {
    private Long callId;
    private Long callTypeFirstId;
    private Long callTypeSecondId;
    private Long callTypeId;
    private String mobile;
    private String userName;
    private String province;
    private String city;
    private String area;
    private String tobaccoCertificateNo;
    private ECallStatus status;
    private Boolean qaStatus;
    private String dealPerson;
    private Date createTs;
    private String createStartDate;
    private String createEndDate;

    public Long getCallId() {
        return callId;
    }

    public void setCallId(Long callId) {
        this.callId = callId;
    }

    public Long getCallTypeFirstId() {
        return callTypeFirstId;
    }

    public void setCallTypeFirstId(Long callTypeFirstId) {
        this.callTypeFirstId = callTypeFirstId;
    }

    public Long getCallTypeSecondId() {
        return callTypeSecondId;
    }

    public void setCallTypeSecondId(Long callTypeSecondId) {
        this.callTypeSecondId = callTypeSecondId;
    }

    public Long getCallTypeId() {
        return callTypeId;
    }

    public void setCallTypeId(Long callTypeId) {
        this.callTypeId = callTypeId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getTobaccoCertificateNo() {
        return tobaccoCertificateNo;
    }

    public void setTobaccoCertificateNo(String tobaccoCertificateNo) {
        this.tobaccoCertificateNo = tobaccoCertificateNo;
    }

    public Boolean getQaStatus() {
        return qaStatus;
    }

    public void setQaStatus(Boolean qaStatus) {
        this.qaStatus = qaStatus;
    }

    public ECallStatus getStatus() {
        return status;
    }

    public void setStatus(ECallStatus status) {
        this.status = status;
    }

    public String getDealPerson() {
        return dealPerson;
    }

    public void setDealPerson(String dealPerson) {
        this.dealPerson = dealPerson;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public String getCreateStartDate() {
        return createStartDate;
    }

    public void setCreateStartDate(String createStartDate) {
        this.createStartDate = createStartDate;
    }

    public String getCreateEndDate() {
        return createEndDate;
    }

    public void setCreateEndDate(String createEndDate) {
        this.createEndDate = createEndDate;
    }
}


