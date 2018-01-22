package com.xinyunlian.jinfu.carbank.api.dto.request;

import com.xinyunlian.jinfu.carbank.api.dto.response.SeriesResponse;

/**
 * @author willwang
 */
public class SeriesRequest extends CarBankTemplate<SeriesResponse> {

    private static final String url = "/resource/mwap/fcar/asset/vehicleSeriesList";

    private String vehicleBrandId;

    public String getVehicleBrandId() {
        return vehicleBrandId;
    }

    public void setVehicleBrandId(String vehicleBrandId) {
        this.vehicleBrandId = vehicleBrandId;
    }

    @Override
    public String getRequestUrl() {
        return url;
    }
}
