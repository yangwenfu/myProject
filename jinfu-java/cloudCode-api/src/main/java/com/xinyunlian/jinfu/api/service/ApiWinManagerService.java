package com.xinyunlian.jinfu.api.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.trade.enums.EBizCode;

import java.util.Map;

/**
 * Created by menglei on 2016年09月23日.
 */
public interface ApiWinManagerService {

    String getmemberNo(Map<String, String> params) throws BizServiceException;

    void saveBankCard(Map<String, String> params) throws BizServiceException;

    Map<String, String> scanPay(Map<String, String> params, EBizCode bizCode) throws BizServiceException;

    Map<String, String> jsPay(Map<String, String> params, EBizCode bizCode) throws BizServiceException;

    void saveRate(Map<String, String> params) throws BizServiceException;

    Map<String, String> getCardbranchNo(Map<String, String> params) throws BizServiceException;

    String getBathMemberNo(Map<String, String> params) throws BizServiceException;

    //String getMccList() throws BizServiceException;

}
