package com.xinyunlian.jinfu.store.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.store.dto.StoreWhiteSignDto;

/**
 * Created by menglei on 2017年07月05日.
 */
public interface StoreWhiteSignService {

    void createSignIn(StoreWhiteSignDto storeWhiteSignDto) throws BizServiceException;

}
