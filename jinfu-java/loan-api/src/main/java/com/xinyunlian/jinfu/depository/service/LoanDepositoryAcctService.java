package com.xinyunlian.jinfu.depository.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.depository.dto.LoanDepositoryAcctDto;

/**
 * @author willwang
 */
public interface LoanDepositoryAcctService {

    LoanDepositoryAcctDto findByBankCardId(Long bankCardId) throws BizServiceException;

    LoanDepositoryAcctDto open(LoanDepositoryAcctDto loanDepositoryAcctDto) throws BizServiceException;
}
