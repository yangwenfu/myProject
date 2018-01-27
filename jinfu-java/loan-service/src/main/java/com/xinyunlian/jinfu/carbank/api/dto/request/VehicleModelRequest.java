package com.xinyunlian.jinfu.carbank.api.dto.request;

import com.xinyunlian.jinfu.carbank.api.dto.response.VehicleModelResponse;

/**
 * Created by dongfangchao on 2017/7/18/0018.
 */
public class VehicleModelRequest extends CarBankTemplate<VehicleModelResponse> {

    private static final String url = "/resource/mwap/fcar/asset/vehicleModelList";

    private String vehicleSeriesId;

    public String getVehicleSeriesId() {
        return vehicleSeriesId;
    }

    public void setVehicleSeriesId(String vehicleSeriesId) {
        this.vehicleSeriesId = vehicleSeriesId;
    }

    @Override
    public String getRequestUrl() {
        return url;
    }
}
