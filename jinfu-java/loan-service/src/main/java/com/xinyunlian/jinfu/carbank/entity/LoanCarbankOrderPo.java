package com.xinyunlian.jinfu.carbank.entity;

import com.xinyunlian.jinfu.carbank.enums.ECbOrderStatus;
import com.xinyunlian.jinfu.carbank.enums.converter.ECbOrderStatusConverter;
import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dongfangchao on 2017/7/18/0018.
 */
@Entity
@Table(name = "loan_carbank_order")
@EntityListeners(IdInjectionEntityListener.class)
public class LoanCarbankOrderPo extends BaseMaintainablePo {
    private static final long serialVersionUID = -3095845214359670762L;

    @Id
    @Column(name = "CB_ORDER_NO")
    private String cbOrderNo;
    @Column(name = "USER_ID")
    private String userId;
    @Column(name = "USER_NAME")
    private String userName;
    @Column(name = "WISH_ORDER_MOBILE")
    private String wishOrderMobile;
    @Column(name = "CITY_ID")
    private Integer cityId;
    @Column(name = "CITY_NAME")
    private String cityName;
    @Column(name = "VEHICLE_BRAND_ID")
    private Integer vehicleBrandId;
    @Column(name = "VEHICLE_BRAND_NAME")
    private String vehicleBrandName;
    @Column(name = "VEHICLE_SERIES_ID")
    private Integer vehicleSeriesId;
    @Column(name = "VEHICLE_SERIES_NAME")
    private String vehicleSeriesName;
    @Column(name = "VEHICLE_MODEL_ID")
    private Integer vehicleModelId;
    @Column(name = "VEHICLE_MODEL_NAME")
    private String vehicleModelName;
    @Column(name = "VEHICLE_REGISTER_DATE")
    private Date vehicleRegisterDate;
    @Column(name = "LOAN_AMT")
    private BigDecimal loanAmt;
    @Column(name = "TERM_LEN")
    private Integer termLen;
    @Column(name = "OUT_TRADE_NO")
    private String outTradeNo;
    @Column(name = "ORDER_STATUS")
    @Convert(converter = ECbOrderStatusConverter.class)
    private ECbOrderStatus orderStatus;
    @Column(name = "REASON")
    private String reason;
    @Column(name = "OVERDUE")
    private Boolean overdue;
    @Column(name = "ISSUE_LOAN_DATE")
    private Date issueLoanDate;

    public String getCbOrderNo() {
        return cbOrderNo;
    }

    public void setCbOrderNo(String cbOrderNo) {
        this.cbOrderNo = cbOrderNo;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getVehicleBrandId() {
        return vehicleBrandId;
    }

    public void setVehicleBrandId(Integer vehicleBrandId) {
        this.vehicleBrandId = vehicleBrandId;
    }

    public String getVehicleBrandName() {
        return vehicleBrandName;
    }

    public void setVehicleBrandName(String vehicleBrandName) {
        this.vehicleBrandName = vehicleBrandName;
    }

    public Integer getVehicleSeriesId() {
        return vehicleSeriesId;
    }

    public void setVehicleSeriesId(Integer vehicleSeriesId) {
        this.vehicleSeriesId = vehicleSeriesId;
    }

    public String getVehicleSeriesName() {
        return vehicleSeriesName;
    }

    public void setVehicleSeriesName(String vehicleSeriesName) {
        this.vehicleSeriesName = vehicleSeriesName;
    }

    public Integer getVehicleModelId() {
        return vehicleModelId;
    }

    public void setVehicleModelId(Integer vehicleModelId) {
        this.vehicleModelId = vehicleModelId;
    }

    public String getVehicleModelName() {
        return vehicleModelName;
    }

    public void setVehicleModelName(String vehicleModelName) {
        this.vehicleModelName = vehicleModelName;
    }

    public BigDecimal getLoanAmt() {
        return loanAmt;
    }

    public void setLoanAmt(BigDecimal loanAmt) {
        this.loanAmt = loanAmt;
    }

    public Integer getTermLen() {
        return termLen;
    }

    public void setTermLen(Integer termLen) {
        this.termLen = termLen;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Boolean getOverdue() {
        return overdue;
    }

    public void setOverdue(Boolean overdue) {
        this.overdue = overdue;
    }

    public Date getIssueLoanDate() {
        return issueLoanDate;
    }

    public void setIssueLoanDate(Date issueLoanDate) {
        this.issueLoanDate = issueLoanDate;
    }

    public ECbOrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(ECbOrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getWishOrderMobile() {
        return wishOrderMobile;
    }

    public void setWishOrderMobile(String wishOrderMobile) {
        this.wishOrderMobile = wishOrderMobile;
    }

    public Date getVehicleRegisterDate() {
        return vehicleRegisterDate;
    }

    public void setVehicleRegisterDate(Date vehicleRegisterDate) {
        this.vehicleRegisterDate = vehicleRegisterDate;
    }
}
