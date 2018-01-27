package com.xinyunlian.jinfu.api.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.trade.dto.YmTradeDto;
import com.xinyunlian.jinfu.yunma.dto.YMMemberDto;

/**
 * Created by menglei on 2016年09月23日.
 */
public interface ApiNotifyService {

    String tradeCallback(YmTradeDto ymTradeDto, YMMemberDto yMMemberDto) throws BizServiceException;

}
