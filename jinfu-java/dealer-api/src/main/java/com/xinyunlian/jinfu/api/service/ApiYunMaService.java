package com.xinyunlian.jinfu.api.service;

import com.xinyunlian.jinfu.api.dto.TradeCountSearchDto;
import com.xinyunlian.jinfu.api.dto.TradeSearchDto;
import com.xinyunlian.jinfu.common.exception.BizServiceException;

/**
 * Created by menglei on 2016年09月23日.
 */
public interface ApiYunMaService {

    String getUserMobile(String openid) throws BizServiceException;

    TradeCountSearchDto getTradeCountByStoreId(TradeCountSearchDto tradeCountSearchDto) throws BizServiceException;

    TradeSearchDto getTradeByStoreId(TradeSearchDto tradeSearchDto) throws BizServiceException;

}
