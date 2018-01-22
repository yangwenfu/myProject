package com.xinyunlian.jinfu.calc.service;

import com.xinyunlian.jinfu.calc.dto.LoanCalcDto;
import com.xinyunlian.jinfu.common.exception.BizServiceException;

import java.math.BigDecimal;
import java.util.Date;

public interface LoanAvgCapAvgIntrCalcService {

    /**
     * 提前还全部计算
     * @param userId 用户编号
     * @param loanId 贷款编号
     * @return
     */
    LoanCalcDto repay(String userId, String loanId) throws BizServiceException;

    /**
     * 提前还本期计算
     * @param userId
     * @param loanId
     * @return
     */
    LoanCalcDto repayAll(String userId, String loanId) throws BizServiceException;


}
