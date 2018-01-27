package com.xinyunlian.jinfu.qrcode.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * 扫码记录
 *
 * @author menglei
 */
@Entity
@Table(name = "scan_code_record")
public class ScanCodeRecordPo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SCAN_CODE_ID")
    private Long scanCodeId;

    @Column(name = "PROD_CODE_ID")
    private Long prodCodeId;

    @Column(name = "SCAN_TOOL")
    private String scanTool;

    @Column(name = "LNG")
    private String lng;

    @Column(name = "LAT")
    private String lat;

    @Column(name = "PROVINCE")
    private String provice;

    @Column(name = "CITY")
    private String city;

    @Column(name = "AREA")
    private String area;

    @Column(name = "ADDRESS")
    private String address;

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
}


