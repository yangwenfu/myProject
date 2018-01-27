package com.xinyunlian.jinfu.store.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.store.dto.StoreWhiteListDto;
import com.xinyunlian.jinfu.store.dto.StoreWhiteListSearchDto;

import java.util.List;

/**
 * Created by menglei on 2017年06月20日.
 */
public interface StoreWhiteListService {

    StoreWhiteListSearchDto getStorePage(StoreWhiteListSearchDto storeWhiteListSearchDto);

    void updateStatus(StoreWhiteListDto storeWhiteListDto) throws BizServiceException;

    void updateRemark(StoreWhiteListDto storeWhiteListDto) throws BizServiceException;

    StoreWhiteListDto findOne(Long id);

    void updateDealerIdByIds(String dealerId, String userId, List<Long> ids) throws BizServiceException;

}
