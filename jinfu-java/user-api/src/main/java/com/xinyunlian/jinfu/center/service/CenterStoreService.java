package com.xinyunlian.jinfu.center.service;

import com.xinyunlian.jinfu.center.dto.CenterStoreDto;

/**
 * Created by King on 2017/5/11.
 */
public interface CenterStoreService {
    public CenterStoreDto findByUuid(String uuid);

    public void addStoreFromMQ(String storeDto);

}
