package com.xinyunlian.jinfu.cmcc.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;

import java.util.Map;

/**
 * Created by menglei on 2016年11月20日.
 */
public interface MskjService {

    Map<String, String> getQueryBalance(String mobile) throws BizServiceException;

    Map<String, String> getDirectPay(String orderId, String mobile, String amount, String storeId, String storeName, String storeAddress) throws BizServiceException;

    Map<String, String> getDirectConf(String orderId, String tradeNo, String amount, String passwd, String storeId) throws BizServiceException;

    Map<String, String> getQueryVouchers(String amount) throws BizServiceException;

    Map<String, String> getQuery(String orderId, String tradeNo) throws BizServiceException;
}
