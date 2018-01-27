package com.xinyunlian.jinfu.fintxhistory.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.fintxhistory.dto.FinTxHistoryDto;
import com.xinyunlian.jinfu.fintxhistory.dto.FinTxHistorySearchDto;

import java.util.List;

/**
 * Created by dongfangchao on 2016/11/21.
 */
public interface FinTxHistoryService {

    /**
     * 分页查询交易记录
     * @param searchDto
     * @return
     * @throws BizServiceException
     */
    FinTxHistorySearchDto getFinTxHistoryPage(FinTxHistorySearchDto searchDto) throws BizServiceException;

    /**
     * 新增交易历史记录
     * @param dto
     * @return
     * @throws BizServiceException
     */
    FinTxHistoryDto addFinTxHistory(FinTxHistoryDto dto) throws BizServiceException;

    /**
     * 更新交易历史记录
     * @param dto
     * @return
     * @throws BizServiceException
     */
    FinTxHistoryDto updateFinTxHistoryStatus(FinTxHistoryDto dto) throws BizServiceException;

    /**
     * 查询全部符合条件的交易记录
     * @param searchDto
     * @return
     * @throws BizServiceException
     */
    List<FinTxHistoryDto> getFinTxHistory(FinTxHistorySearchDto searchDto) throws BizServiceException;

}
