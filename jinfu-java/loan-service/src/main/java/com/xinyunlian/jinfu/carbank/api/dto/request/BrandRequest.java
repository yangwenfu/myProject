package com.xinyunlian.jinfu.carbank.api.dto.request;

import com.xinyunlian.jinfu.carbank.api.dto.response.BrandResponse;

/**
 * Created by Administrator on 2017/6/16 0016.
 */
public class BrandRequest extends CarBankTemplate<BrandResponse>{

    private static final String url = "/resource/mwap/fcar/asset/vehicleBrandList";

    @Override
    public String getRequestUrl() {
        return url;
    }
}
