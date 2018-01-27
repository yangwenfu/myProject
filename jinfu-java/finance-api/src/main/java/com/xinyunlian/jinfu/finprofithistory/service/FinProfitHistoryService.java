package com.xinyunlian.jinfu.finprofithistory.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.finprofithistory.dto.FinProfitHistoryDto;
import com.xinyunlian.jinfu.finprofithistory.dto.FinProfitHistorySearchDto;
import com.xinyunlian.jinfu.finprofithistory.dto.FinProfitHistorySumDto;
import com.xinyunlian.jinfu.finprofithistory.dto.FinProfitHistorySumSearchDto;

import java.util.Date;
import java.util.List;

/**
 * Created by dongfangchao on 2016/11/21.
 */
public interface FinProfitHistoryService {

    /**
     * 获取昨日收益
     * @param searchDto
     * @return
     * @throws BizServiceException
     */
    List<FinProfitHistoryDto> getProfitHistory(FinProfitHistorySearchDto searchDto) throws BizServiceException;

    /**
     * 查询每日收益总计
     * @param searchDto
     * @return
     * @throws BizServiceException
     */
    List<FinProfitHistorySumDto> getProfitHistorySum(FinProfitHistorySumSearchDto searchDto) throws BizServiceException;

    /**
     * 分页查询每日收益总计
     * @param searchDto
     * @return
     * @throws BizServiceException
     */
    FinProfitHistorySumSearchDto getProfitHistorySumPage(FinProfitHistorySumSearchDto searchDto) throws BizServiceException;

    /**
     * 更新理财收益记录，按用户理财产品每日
     * @param finProfitHistoryDto
     * @throws BizServiceException
     */
    void updateProfitHistory(FinProfitHistoryDto finProfitHistoryDto) throws BizServiceException;

    /**
     * 更新理财收益统计信息，按用户每日
     * @param userId
     * @param profitDate
     * @throws BizServiceException
     */
    void updateProfitHistorySummary(String userId, Date profitDate) throws BizServiceException;

}
