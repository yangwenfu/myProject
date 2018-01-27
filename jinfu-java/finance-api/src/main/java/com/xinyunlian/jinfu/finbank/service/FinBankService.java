package com.xinyunlian.jinfu.finbank.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.finbank.dto.FinBankDto;
import com.xinyunlian.jinfu.finbank.dto.FinBankSearchDto;

import java.util.List;

/**
 * Created by dongfangchao on 2016/11/24/0024.
 */
public interface FinBankService {

    /**
     * 获取中融银行卡列表
     * @param searchDto
     * @return
     */
    List<FinBankDto> getFinBankList(FinBankSearchDto searchDto) throws BizServiceException;

}
