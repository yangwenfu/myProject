package com.xinyunlian.jinfu.order.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.order.dto.CmccOrderDto;
import com.xinyunlian.jinfu.order.dto.CmccOrderInfoDto;

import java.util.List;
import java.util.Map;

/**
 * Created by menglei on 2016年11月20日.
 */
public interface CmccOrderService {

    Map<String, String> queryBalance(CmccOrderDto cmccOrderDto) throws BizServiceException;

    Map<String, String> saveOrder(CmccOrderDto cmccOrderDto) throws BizServiceException;

    Map<String, String> confirmOrder(CmccOrderDto cmccOrderDto) throws BizServiceException;

    Map<String, String> queryVouchers(CmccOrderDto cmccOrderDto) throws BizServiceException;

    CmccOrderInfoDto saveCmccOrderInfo(CmccOrderInfoDto cmccOrderInfoDto);

    List<Object[]> getOrderList(String type);

    CmccOrderInfoDto getOrderByOrderNo(String orderNo);

}
