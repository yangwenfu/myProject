package com.xinyunlian.jinfu.shopkeeper.dto.carbank;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by dongfangchao on 2017/7/18/0018.
 */
public class WishOrderRequest implements Serializable {

    private static final long serialVersionUID = 5001715287561135467L;

    @NotBlank(message = "wishOrderMobile can not be blank")
    private String wishOrderMobile;

    @NotNull(message = "cityId can not be null")
    private Integer cityId;

    @NotBlank(message = "cityName can not be blank")
    private String cityName;

    @NotNull(message = "vehicleBrandId can not be null")
    private Integer vehicleBrandId;

    @NotBlank(message = "vehicleBrandName can not be blank")
    private String vehicleBrandName;

    @NotNull(message = "vehicleSeriesId can not be null")
    private Integer vehicleSeriesId;

    @NotBlank(message = "vehicleSeriesName can not be blank")
    private String vehicleSeriesName;

    @NotNull(message = "vehicleModelId can not be null")
    private Integer vehicleModelId;

    @NotBlank(message = "vehicleModelName can not be blank")
    private String vehicleModelName;

    @NotNull(message = "vehicleRegisterDate can not be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date vehicleRegisterDate;

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
