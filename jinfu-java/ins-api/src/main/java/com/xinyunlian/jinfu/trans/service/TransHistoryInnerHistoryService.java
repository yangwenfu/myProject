package com.xinyunlian.jinfu.trans.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.trans.dto.TransHistoryDto;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
public interface TransHistoryInnerHistoryService {

    /**
     * 新增交易历史记录
     * @param transHistoryDto
     * @return
     * @throws BizServiceException
     */
    TransHistoryDto addTransHistory(TransHistoryDto transHistoryDto) throws BizServiceException;

    /**
     * 更新交易历史记录
     * @param transHistoryDto
     * @throws BizServiceException
     */
    void updateTransHistory(TransHistoryDto transHistoryDto) throws BizServiceException;

}
