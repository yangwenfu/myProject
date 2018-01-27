package com.xinyunlian.jinfu.balance.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;

/**
 * Created by Willwang on 2017/5/19.
 */
public interface BalanceCashierService {
    /**
     * 更新账单
     */
    void refresh(Long outlineId) throws BizServiceException;

    /**
     * 自动更新当日账单
     * @throws BizServiceException
     */
    void autoRefresh() throws BizServiceException;

    /**
     * 监听MQ自动拉取账单
     * @param json
     * @throws BizServiceException
     */
    void updateFromMQ(String json);

}
