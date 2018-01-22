package com.xinyunlian.jinfu.yunma.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.yunma.enums.EDepotReceiveStatus;
import com.xinyunlian.jinfu.yunma.enums.EDepotStatus;
import com.xinyunlian.jinfu.yunma.enums.converter.EDepotReceiveStatusConverter;
import com.xinyunlian.jinfu.yunma.enums.converter.EDepotStatusConverter;

import javax.persistence.*;

/**
 * Created by menglei on 2017-08-28.
 */
@Entity
@Table(name = "ym_depot")
public class YmDepotPo extends BaseMaintainablePo {
    private static final long serialVersionUID = 2962130238021106783L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "QRCODE_NO")
    private String qrCodeNo;

    @Column(name = "QRCODE_URL")
    private String qrCodeUrl;

    @Column(name = "REMARK")
    private String remark;

    @Convert(converter = EDepotStatusConverter.class)
    @Column(name = "STATUS")
    private EDepotStatus status;

    @Convert(converter = EDepotReceiveStatusConverter.class)
    @Column(name = "RECEIVE_STATUS")
    private EDepotReceiveStatus receiveStatus;

    @Column(name = "MAIL_NAME")
    private String mailName;

    @Column(name = "MAIL_MOBILE")
    private String mailMobile;

    @Column(name = "MAIL_ADDRESS")
    private String mailAddress;

    @Column(name = "BATCH_NO")
    private String batchNo;

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

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
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
