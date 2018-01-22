package com.xinyunlian.jinfu.uplus.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2017年03月09日.
 */
public class ProdCodeReportDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String qrCodeNo;

    private String qrCodeUrl;

    private String createTime;

    private String bindTime;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getBindTime() {
        return bindTime;
    }

    public void setBindTime(String bindTime) {
        this.bindTime = bindTime;
    }
}
