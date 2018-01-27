package com.xinyunlian.jinfu.bank.service;

import com.xinyunlian.jinfu.bank.dto.CorpBankDto;
import com.xinyunlian.jinfu.common.exception.BizServiceException;

/**
 * Created by dongfangchao on 2017/5/18/0018.
 */
public interface CorpBankService {

    CorpBankDto getCorpBankByUserId(String userId) throws BizServiceException;

    CorpBankDto addCorpBank(CorpBankDto dto) throws BizServiceException;

    CorpBankDto getCorpBankById(Long id) throws BizServiceException;

}
