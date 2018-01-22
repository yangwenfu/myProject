package com.xinyunlian.jinfu.yunma.dto;

import com.xinyunlian.jinfu.yunma.enums.EDepotReceiveStatus;
import com.xinyunlian.jinfu.yunma.enums.EDepotStatus;

import java.io.Serializable;

/**
 * Created by menglei on 2017-08-29.
 */
public class YmDepotDto implements Serializable {

    private static final long serialVersionUID = -8951652034987940359L;

    private Long id;

    private String qrCodeNo;

    private String qrCodeUrl;

    private String remark;

    private EDepotStatus status;

    private EDepotReceiveStatus receiveStatus;

    private String batchNo;

    private String mailName;

    private String mailMobile;

    private String mailAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQrCodeNo() {
        return qrCodeNo;
    }

    public void setQrCodeNo(String qrCodeNo) {
        this.qrCodeNo = qrCodeNo;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public EDepotStatus getStatus() {
        return status;
    }

    public void setStatus(EDepotStatus status) {
        this.status = status;
    }

    public EDepotReceiveStatus getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(EDepotReceiveStatus receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getMailName() {
        return mailName;
    }

    public void setMailName(String mailName) {
        this.mailName = mailName;
    }

    public String getMailMobile() {
        return mailMobile;
    }

    public void setMailMobile(String mailMobile) {
        this.mailMobile = mailMobile;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }
}
