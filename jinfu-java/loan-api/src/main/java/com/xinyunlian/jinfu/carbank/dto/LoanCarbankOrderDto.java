package com.xinyunlian.jinfu.carbank.dto;

import com.xinyunlian.jinfu.carbank.enums.ECbOrderStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by dongfangchao on 2017/7/18/0018.
 */
public class LoanCarbankOrderDto implements Serializable {
    private static final long serialVersionUID = -3095845214359670762L;

    private String cbOrderNo;

    private String userId;

    private String userName;

    private String wishOrderMobile;

    private Integer cityId;

    private String cityName;

    private Integer vehicleBrandId;

    private String vehicleBrandName;

    private Integer vehicleSeriesId;

    private String vehicleSeriesName;

    private Integer vehicleModelId;

    private String vehicleModelName;

    private Date vehicleRegisterDate;

    private BigDecimal loanAmt;

    private Integer termLen;

    private String outTradeNo;

    private ECbOrderStatus orderStatus;

    private String reason;

    private Boolean overdue;

    private Date issueLoanDate;

    private Date lastMntTs;

    private Date createTs;

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

    public Date getLastMntTs() {
        return lastMntTs;
    }

    public void setLastMntTs(Date lastMntTs) {
        this.lastMntTs = lastMntTs;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }
}
