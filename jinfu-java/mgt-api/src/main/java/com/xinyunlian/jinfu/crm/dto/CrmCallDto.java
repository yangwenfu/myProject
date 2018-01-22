package com.xinyunlian.jinfu.crm.dto;

import com.xinyunlian.jinfu.crm.enums.ECallStatus;
import com.xinyunlian.jinfu.crm.enums.ECrmUserStatus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 客户通话记录Entity
 *
 * @author jll
 */

public class CrmCallDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long callId;
    private Long callTypeFirstId;
    private Long callTypeSecondId;
    private Long callTypeId;
    private String callTypeName;
    private String callTypePath;
    private String mobile;
    private String userName;
    private String storeName;
    private String userId;
    private String province;
    private String city;
    private String area;
    private String tobaccoCertificateNo;
    private ECallStatus status;
    private String dealPerson;
    private String takePerson;
    private String jobNo;
    private String createOpid;
    private String content;
    private Date createTs;
    private String feedback;
    private ECrmUserStatus userStatus;
    private Boolean qaStatus = false;
    private String qaNote;
    List<CrmCallNoteDto> callNoteDtos = new ArrayList<>();

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

    public String getCallTypeName() {
        return callTypeName;
    }

    public void setCallTypeName(String callTypeName) {
        this.callTypeName = callTypeName;
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getTakePerson() {
        return takePerson;
    }

    public void setTakePerson(String takePerson) {
        this.takePerson = takePerson;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getCreateOpid() {
        return createOpid;
    }

    public void setCreateOpid(String createOpid) {
        this.createOpid = createOpid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public List<CrmCallNoteDto> getCallNoteDtos() {
        return callNoteDtos;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getCallTypePath() {
        return callTypePath;
    }

    public void setCallTypePath(String callTypePath) {
        this.callTypePath = callTypePath;
    }

    public void setCallNoteDtos(List<CrmCallNoteDto> callNoteDtos) {
        this.callNoteDtos = callNoteDtos;
    }

    public Boolean getQaStatus() {
        return qaStatus;
    }

    public void setQaStatus(Boolean qaStatus) {
        this.qaStatus = qaStatus;
    }

    public String getQaNote() {
        return qaNote;
    }

    public void setQaNote(String qaNote) {
        this.qaNote = qaNote;
    }

    public ECrmUserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(ECrmUserStatus userStatus) {
        this.userStatus = userStatus;
    }
}


