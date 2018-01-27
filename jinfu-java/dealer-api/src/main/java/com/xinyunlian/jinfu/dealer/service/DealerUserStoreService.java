package com.xinyunlian.jinfu.dealer.service;

import com.xinyunlian.jinfu.dealer.dto.DealerUserStoreDto;

import java.util.List;

/**
 * Created by menglei on 2016年08月30日.
 */
public interface DealerUserStoreService {

    long getCount(DealerUserStoreDto dealerUserStoreDto);

    List<DealerUserStoreDto> getListByStoreId(String storeId);

    List<DealerUserStoreDto> getStoreListByUserId(DealerUserStoreDto dealerUserStoreDto);

    void createDealer(DealerUserStoreDto dealerUserStoreDto);

}
