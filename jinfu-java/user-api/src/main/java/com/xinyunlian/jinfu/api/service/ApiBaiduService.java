package com.xinyunlian.jinfu.api.service;

import com.xinyunlian.jinfu.api.dto.ApiBaiduDto;
import com.xinyunlian.jinfu.store.dto.StoreInfDto;

/**
 * Created by menglei on 2016年11月02日.
 */
public interface ApiBaiduService {

    String getPoint(String fullAddress);

    void updatePoint(StoreInfDto storeInfDto);

    ApiBaiduDto getArea(String location);

}
