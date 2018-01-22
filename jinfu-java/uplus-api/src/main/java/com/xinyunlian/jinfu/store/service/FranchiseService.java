package com.xinyunlian.jinfu.store.service;

import com.xinyunlian.jinfu.store.dto.FranchiseDto;

import java.util.List;

/**
 * Created by menglei on 2017年03月24日.
 */
public interface FranchiseService {

    FranchiseDto getByStoreId(Long storeId);

    List<FranchiseDto> getByStoreIds(List<Long> storeIds);

}
