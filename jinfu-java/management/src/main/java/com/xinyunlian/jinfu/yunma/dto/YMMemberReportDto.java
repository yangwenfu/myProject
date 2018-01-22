package com.xinyunlian.jinfu.yunma.dto;

import com.xinyunlian.jinfu.yunma.enums.EMemberStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * 云码商铺Entity
 *
 * @author jll
 */

public class YMMemberReportDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private String memberNo;
    private String qrcodeNo;
    private String storeName;
    private String userName;
    private String fullArea;
    private Date bindTime;
    private EMemberStatus memberStatus;

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getQrcodeNo() {
        return qrcodeNo;
    }

    public void setQrcodeNo(String qrcodeNo) {
        this.qrcodeNo = qrcodeNo;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullArea() {
        return fullArea;
    }

    public void setFullArea(String fullArea) {
        this.fullArea = fullArea;
    }

    public Date getBindTime() {
        return bindTime;
    }

    public void setBindTime(Date bindTime) {
        this.bindTime = bindTime;
    }

    public EMemberStatus getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(EMemberStatus memberStatus) {
        this.memberStatus = memberStatus;
    }
}


