package com.xinyunlian.jinfu.qrcode.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;
import com.xinyunlian.jinfu.qrcode.enums.EProdCodeStatus;

/**
 * Created by menglei on 2017年03月14日.
 */
public class ProdCodeViewSearchDto extends PagingDto<ProdCodeViewDto> {

    private static final long serialVersionUID = 1L;

    private String qrCodeNo;

    private String prodName;

    private String storeName;

    private Boolean frozen;

    private Long areaId;

    private Long provinceId;

    private Long cityId;

    private String province;

    private String city;

    private String area;

    private String startBindTs;

    private String endBindTs;

    private String startSellTs;

    private String endSellTs;

    private EProdCodeStatus status;

    public String getQrCodeNo() {
        return qrCodeNo;
    }

    public void setQrCodeNo(String qrCodeNo) {
        this.qrCodeNo = qrCodeNo;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Boolean getFrozen() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
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

    public String getStartBindTs() {
        return startBindTs;
    }

    public void setStartBindTs(String startBindTs) {
        this.startBindTs = startBindTs;
    }

    public String getEndBindTs() {
        return endBindTs;
    }

    public void setEndBindTs(String endBindTs) {
        this.endBindTs = endBindTs;
    }

    public String getStartSellTs() {
        return startSellTs;
    }

    public void setStartSellTs(String startSellTs) {
        this.startSellTs = startSellTs;
    }

    public String getEndSellTs() {
        return endSellTs;
    }

    public void setEndSellTs(String endSellTs) {
        this.endSellTs = endSellTs;
    }

    public EProdCodeStatus getStatus() {
        return status;
    }

    public void setStatus(EProdCodeStatus status) {
        this.status = status;
    }
}
