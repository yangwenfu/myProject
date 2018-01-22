package com.xinyunlian.jinfu.dealer.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserLogType;
import com.xinyunlian.jinfu.dealer.enums.converter.EDealerUserLogTypeConverter;
import com.xinyunlian.jinfu.user.entity.DealerUserPo;

import javax.persistence.*;

/**
 * Created by menglei on 2016年08月26日.
 */
@Entity
@Table(name = "dealer_user_log")
public class DealerUserLogPo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOG_ID")
    private Long logId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "DEALER_ID")
    private String dealerId;

    @Column(name = "LNG")
    private String lng;

    @Column(name = "LAT")
    private String lat;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "TYPE")
    @Convert(converter = EDealerUserLogTypeConverter.class)
    private EDealerUserLogType type;

    @Column(name = "STORE_USER_ID")
    private String storeUserId;

    @Column(name = "STORE_ID")
    private String storeId;

    @Column(name = "REMARK")
    private String remark;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    private DealerUserPo dealerUserPo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DEALER_ID", insertable = false, updatable = false)
    private DealerPo dealerPo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public EDealerUserLogType getType() {
        return type;
    }

    public void setType(EDealerUserLogType type) {
        this.type = type;
    }

    public String getStoreUserId() {
        return storeUserId;
    }

    public void setStoreUserId(String storeUserId) {
        this.storeUserId = storeUserId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public DealerUserPo getDealerUserPo() {
        return dealerUserPo;
    }

    public void setDealerUserPo(DealerUserPo dealerUserPo) {
        this.dealerUserPo = dealerUserPo;
    }

    public DealerPo getDealerPo() {
        return dealerPo;
    }

    public void setDealerPo(DealerPo dealerPo) {
        this.dealerPo = dealerPo;
    }
}
