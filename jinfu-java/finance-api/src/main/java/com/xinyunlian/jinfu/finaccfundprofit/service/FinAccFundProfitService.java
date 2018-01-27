package com.xinyunlian.jinfu.finaccfundprofit.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.finaccfundprofit.dto.FinAccFundProfitDto;

import java.util.List;

/**
 * Created by dongfangchao on 2016/11/23.
 */
public interface FinAccFundProfitService {

    /**
     * 根据条件查询持有的资产
     * @param searchDto
     * @return
     */
    List<FinAccFundProfitDto> getFinAccProdProfits(FinAccFundProfitDto searchDto);

    /**
     * 更新收益统计信息
     * @param dto
     */
    void updateFinAccProdProfit(FinAccFundProfitDto dto);

    /**
     * 根据第三方理财账号查询持有资产
     * @param extTxAccId
     * @return
     * @throws BizServiceException
     */
    FinAccFundProfitDto getFinAccHoldAsset(String extTxAccId) throws BizServiceException;

}
