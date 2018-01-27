package com.xinyunlian.jinfu.qrcode.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 扫码记录
 *
 * @author menglei
 */
public class ScanCodeRecordDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long scanCodeId;

    private Long prodCodeId;

    private String scanTool;

    private String lng;

    private String lat;

    private String provice;

    private String city;

    private String area;

    private String address;

    private Date createTs;

    public Long getScanCodeId() {
        return scanCodeId;
    }

    public void setScanCodeId(Long scanCodeId) {
        this.scanCodeId = scanCodeId;
    }

    public Long getProdCodeId() {
        return prodCodeId;
    }

    public void setProdCodeId(Long prodCodeId) {
        this.prodCodeId = prodCodeId;
    }

    public String getScanTool() {
        return scanTool;
    }

    public void setScanTool(String scanTool) {
        this.scanTool = scanTool;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getProvice() {
        return provice;
    }

    public void setProvice(String provice) {
        this.provice = provice;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }
}


