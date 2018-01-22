package com.xinyunlian.jinfu.balance.service;

import com.xinyunlian.jinfu.balance.dto.BalanceOutlineDto;
import com.xinyunlian.jinfu.common.exception.BizServiceException;

import java.util.List;

/**
 * Created by Willwang on 2017/5/19.
 */
public interface BalanceOutlineService {

    /**
     * 生成前一天的还款概况
     */
    void generate();

    /**
     * 强制性结束对账
     */
    void finish(String mgtUserId, Long outlineId) throws BizServiceException;

    /**
     * 更新自动勾对状态
     */
    void updateAutoed(Long outlineId, boolean autoed) throws BizServiceException;

    /**
     * 显示某个时间段内的对账概要
     */
    List<BalanceOutlineDto> listByDate(String start, String end);

}
