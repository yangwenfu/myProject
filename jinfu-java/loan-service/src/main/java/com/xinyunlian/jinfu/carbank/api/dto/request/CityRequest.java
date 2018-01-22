package com.xinyunlian.jinfu.carbank.api.dto.request;

import com.xinyunlian.jinfu.carbank.api.dto.response.CityResponse;

/**
 * Created by Administrator on 2017/6/16 0016.
 */
public class CityRequest extends CarBankTemplate<CityResponse> {

    private static final String url = "/resource/mwap/fcar/admin/queryEffectiveCityList";

    @Override
    public String getRequestUrl() {
        return url;
    }
}
