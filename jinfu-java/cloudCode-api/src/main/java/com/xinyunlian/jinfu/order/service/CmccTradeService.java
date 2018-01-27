package com.xinyunlian.jinfu.order.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.order.dto.CmccTradeRecordDto;
import com.xinyunlian.jinfu.order.enums.ECmccOrderTradeStatus;

import java.util.List;

/**
 * Created by menglei on 2016年11月20日.
 */
public interface CmccTradeService {

    List<CmccTradeRecordDto> startTrade(List<Object[]> storeList, List<Object[]> bankList) throws BizServiceException;

    void updateTradeStatus(CmccTradeRecordDto cmccTradeRecordDto, ECmccOrderTradeStatus tradeStatus) throws BizServiceException;

    List<CmccTradeRecordDto> findTradeList(ECmccOrderTradeStatus tradeStatus) throws BizServiceException;

}
