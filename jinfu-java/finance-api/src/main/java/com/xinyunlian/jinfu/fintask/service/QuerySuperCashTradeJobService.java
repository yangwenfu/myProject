package com.xinyunlian.jinfu.fintask.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;

/**
 * Created by dongfangchao on 2017/6/19/0019.
 */
public interface QuerySuperCashTradeJobService {

    /**
     * 查询申购交易结果
     * @throws BizServiceException
     */
    void queryApplyPurTrade() throws BizServiceException;

    /**
     * 查询赎回交易结果
     * @throws BizServiceException
     */
    void queryRedeemTrade() throws BizServiceException;

}
