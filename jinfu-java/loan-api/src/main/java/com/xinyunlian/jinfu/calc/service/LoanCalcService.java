package com.xinyunlian.jinfu.calc.service;

import com.xinyunlian.jinfu.calc.dto.LoanCalcDto;
import com.xinyunlian.jinfu.common.exception.BizServiceException;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Willwang on 2017/1/11.
 */
public interface LoanCalcService {

    /**
     * 提前还全部计算
     * @param userId 用户编号
     * @param loanId 贷款编号
     * @return
     */
    LoanCalcDto prepayAll(String userId, String loanId) throws BizServiceException;

    /**
     * 提前还本期计算
     * @param userId
     * @param loanId
     * @return
     */
    LoanCalcDto prepayCurrent(String userId, String loanId) throws BizServiceException;

    /**
     * 等额本息按期还款
     * @param userId
     * @param loanId
     * @param period 期
     * @param date 可自定义扣款时间
     * @return
     * @throws BizServiceException
     */
    LoanCalcDto period(String userId, String loanId, Integer period, Date date) throws BizServiceException;

    /**
     * 还任意本金计算
     * @param userId
     * @param loanId
     * @param date
     * @return
     */
    LoanCalcDto  any(String userId, String loanId, BigDecimal capital, Date date) throws BizServiceException;

    /**
     * 强制性计算还任意本金
     * @param userId
     * @param loanId
     * @param capital
     * @param date
     * @return
     * @throws BizServiceException
     */
    LoanCalcDto anyForce(String userId, String loanId, BigDecimal capital, Date date) throws BizServiceException;

}
