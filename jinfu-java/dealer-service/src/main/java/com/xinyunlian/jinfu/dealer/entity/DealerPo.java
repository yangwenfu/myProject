package com.xinyunlian.jinfu.dealer.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;
import com.xinyunlian.jinfu.dealer.enums.EDealerAuditStatus;
import com.xinyunlian.jinfu.dealer.enums.EDealerStatus;
import com.xinyunlian.jinfu.dealer.enums.EDealerType;
import com.xinyunlian.jinfu.dealer.enums.converter.EDealerAuditStatusConverter;
import com.xinyunlian.jinfu.dealer.enums.converter.EDealerStatusConverter;
import com.xinyunlian.jinfu.dealer.enums.converter.EDealerTypeConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by menglei on 2016年08月26日.
 */
@Entity
@Table(name = "dealer")
@EntityListeners(IdInjectionEntityListener.class)
public class DealerPo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "DEALER_ID")
    private String dealerId;

    @Column(name = "DEALER_NAME")
    private String dealerName;

    @Column(name = "LEVEL_ID")
    private Long levelId;

    @Column(name = "INDUSTRY_ID")
    private String industryId;

    @Column(name = "AREA_ID")
    private String areaId;

    @Column(name = "PROVINCE_ID")
    private String provinceId;

    @Column(name = "CITY_ID")
    private String cityId;

    @Column(name = "DISTRICT_ID")
    private String districtId;

    @Column(name = "STREET_ID")
    private String streetId;

    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "CITY")
    private String city;

    @Column(name = "AREA")
    private String area;

    @Column(name = "STREET")
    private String street;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "TYPE")
    @Convert(converter = EDealerTypeConverter.class)
    private EDealerType type;

    @Column(name = "BEGIN_TIME")
    private String beginTime;

    @Column(name = "END_TIME")
    private String endTime;

    @Column(name = "AUDIT_STATUS")
    @Convert(converter = EDealerAuditStatusConverter.class)
    private EDealerAuditStatus auditStatus;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "STATUS")
    @Convert(converter = EDealerStatusConverter.class)
    private EDealerStatus status;

    @Column(name = "BELONG_ID")
    private String belongId;

    @Column(name = "SERVICE_FEE_RT")
    private BigDecimal serviceFeeRt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEALER_ID", insertable = false, updatable = false)
    private DealerExtraPo dealerExtraPo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "LEVEL_ID", insertable = false, updatable = false)
    private DealerLevelPo dealerLevelPo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "dealerPo")
    private List<DealerProdPo> dealerProdPoList;

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getIndustryId() {
        return industryId;
    }

    public void setIndustryId(String industryId) {
        this.industryId = industryId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public EDealerType getType() {
        return type;
    }

    public void setType(EDealerType type) {
        this.type = type;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public EDealerStatus getStatus() {
        return status;
    }

    public void setStatus(EDealerStatus status) {
        this.status = status;
    }

    public DealerExtraPo getDealerExtraPo() {
        return dealerExtraPo;
    }

    public void setDealerExtraPo(DealerExtraPo dealerExtraPo) {
        this.dealerExtraPo = dealerExtraPo;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getStreetId() {
        return streetId;
    }

    public void setStreetId(String streetId) {
        this.streetId = streetId;
    }

    public DealerLevelPo getDealerLevelPo() {
        return dealerLevelPo;
    }

    public void setDealerLevelPo(DealerLevelPo dealerLevelPo) {
        this.dealerLevelPo = dealerLevelPo;
    }

    public Long getLevelId() {
        return levelId;
    }

    public void setLevelId(Long levelId) {
        this.levelId = levelId;
    }

    public List<DealerProdPo> getDealerProdPoList() {
        return dealerProdPoList;
    }

    public void setDealerProdPoList(List<DealerProdPo> dealerProdPoList) {
        this.dealerProdPoList = dealerProdPoList;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public EDealerAuditStatus getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(EDealerAuditStatus auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBelongId() {
        return belongId;
    }

    public void setBelongId(String belongId) {
        this.belongId = belongId;
    }

    public BigDecimal getServiceFeeRt() {
        return serviceFeeRt;
    }

    public void setServiceFeeRt(BigDecimal serviceFeeRt) {
        this.serviceFeeRt = serviceFeeRt;
    }
}
