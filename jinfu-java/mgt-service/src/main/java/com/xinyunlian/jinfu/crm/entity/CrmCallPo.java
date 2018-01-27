package com.xinyunlian.jinfu.crm.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.crm.enums.ECallStatus;
import com.xinyunlian.jinfu.crm.enums.ECrmUserStatus;
import com.xinyunlian.jinfu.crm.enums.converter.ECallStatusConverter;
import com.xinyunlian.jinfu.crm.enums.converter.ECrmUserStatusConverter;

import javax.persistence.*;

/**
 * 客户通话记录Entity
 *
 * @author jll
 */
@Entity
@Table(name = "CRM_CALL")
public class CrmCallPo extends BaseMaintainablePo {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CALL_ID")
    private Long callId;

    @Column(name = "CALL_TYPE_FIRST_ID")
    private Long callTypeFirstId;

    @Column(name = "CALL_TYPE_SECOND_ID")
    private Long callTypeSecondId;

    @Column(name = "CALL_TYPE_ID")
    private Long callTypeId;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "STORE_NAME")
    private String storeName;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "CITY")
    private String city;

    @Column(name = "AREA")
    private String area;

    @Column(name = "TOBACCO_CERTIFICATE_NO")
    private String tobaccoCertificateNo;

    @Column(name = "STATUS")
    @Convert(converter = ECallStatusConverter.class)
    private ECallStatus status;

    @Column(name = "DEAL_PERSON")
    private String dealPerson;

    @Column(name = "TAKE_PERSON")
    private String takePerson;

    @Column(name = "JOB_NO")
    private String jobNo;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "FEEDBACK")
    private String feedback;

    @Column(name = "USER_STATUS")
    @Convert(converter = ECrmUserStatusConverter.class)
    private ECrmUserStatus userStatus;

    @Column(name = "QA_STATUS")
    private Boolean qaStatus;

    @Column(name = "QA_NOTE")
    private String qaNote;

    public void setCallId(Long callId) {
        this.callId = callId;
    }

    public Long getCallId() {
        return callId;
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

    public void setCallTypeId(Long callTypeId) {
        this.callTypeId = callTypeId;
    }

    public Long getCallTypeId() {
        return callTypeId;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince() {
        return province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public void setTobaccoCertificateNo(String tobaccoCertificateNo) {
        this.tobaccoCertificateNo = tobaccoCertificateNo;
    }

    public String getTobaccoCertificateNo() {
        return tobaccoCertificateNo;
    }

    public ECallStatus getStatus() {
        return status;
    }

    public void setStatus(ECallStatus status) {
        this.status = status;
    }

    public void setDealPerson(String dealPerson) {
        this.dealPerson = dealPerson;
    }

    public String getDealPerson() {
        return dealPerson;
    }

    public String getTakePerson() {
        return takePerson;
    }

    public void setTakePerson(String takePerson) {
        this.takePerson = takePerson;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public ECrmUserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(ECrmUserStatus userStatus) {
        this.userStatus = userStatus;
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
}


