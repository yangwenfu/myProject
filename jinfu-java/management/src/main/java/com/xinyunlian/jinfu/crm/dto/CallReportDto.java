package com.xinyunlian.jinfu.crm.dto;

import com.xinyunlian.jinfu.crm.enums.ECallStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户通话记录Entity
 *
 * @author jll
 */

public class CallReportDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Date createTs;
    private String mobile;
    private String userName;
    private String tobaccoCertificateNo;
    private String province;
    private String city;
    private String area;
    private String callTypeFirstName;
    private String callTypeSecondName;
    private String callTypeName;
    private String content;
    private String dealContent;
    private String dealPerson;
    private ECallStatus status;
    private String jobNo;
    private String takePerson;
    private String feedback;
    private String qaStatus;;
    private String qaNote;

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
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

    public String getCallTypeFirstName() {
        return callTypeFirstName;
    }

    public void setCallTypeFirstName(String callTypeFirstName) {
        this.callTypeFirstName = callTypeFirstName;
    }

    public String getCallTypeSecondName() {
        return callTypeSecondName;
    }

    public void setCallTypeSecondName(String callTypeSecondName) {
        this.callTypeSecondName = callTypeSecondName;
    }

    public String getCallTypeName() {
        return callTypeName;
    }

    public void setCallTypeName(String callTypeName) {
        this.callTypeName = callTypeName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDealContent() {
        return dealContent;
    }

    public void setDealContent(String dealContent) {
        this.dealContent = dealContent;
    }

    public String getDealPerson() {
        return dealPerson;
    }

    public void setDealPerson(String dealPerson) {
        this.dealPerson = dealPerson;
    }

    public ECallStatus getStatus() {
        return status;
    }

    public void setStatus(ECallStatus status) {
        this.status = status;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getTakePerson() {
        return takePerson;
    }

    public void setTakePerson(String takePerson) {
        this.takePerson = takePerson;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getQaStatus() {
        return qaStatus;
    }

    public void setQaStatus(String qaStatus) {
        this.qaStatus = qaStatus;
    }

    public String getQaNote() {
        return qaNote;
    }

    public void setQaNote(String qaNote) {
        this.qaNote = qaNote;
    }
}


