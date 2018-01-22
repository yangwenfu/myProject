package com.xinyunlian.jinfu.balance.service;

import com.xinyunlian.jinfu.balance.dto.BalanceDetailDto;
import com.xinyunlian.jinfu.balance.dto.BalanceDetailListDto;
import com.xinyunlian.jinfu.common.exception.BizServiceException;

/**
 * Created by Willwang on 2017/5/19.
 */
public interface BalanceDetailService {

    /**
     * 对账详情
     * @param detailId
     * @return
     * @throws BizServiceException
     */
    BalanceDetailDto detail(Long detailId) throws BizServiceException;

    /**
     * 对账列表
     * @param outlineId
     * @return
     * @throws BizServiceException
     */
    BalanceDetailListDto list(Long outlineId) throws BizServiceException;

    /**
     * 自动对账
     */
    void auto(Long outlineId) throws BizServiceException;

    /**
     * 取消勾对
     * @param detailId
     * @throws BizServiceException
     */
    void cancel(Long detailId) throws BizServiceException;

    /**
     * 手动勾对
     * 可修改如下信息
     * 还款通道、备注信息、每一个还款计划的实际还款时间，还款状态、实际还款金额
     */
    void manual(String mgtUserId, BalanceDetailDto balanceDetailDto);

}
