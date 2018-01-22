package com.xinyunlian.jinfu.fintask.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;

/**
 * Created by dongfangchao on 2017/6/5/0005.
 */
public interface QuotationJobService {

    /**
     * 更新行情
     * @throws BizServiceException
     */
    void updateQuotation() throws BizServiceException;

}
