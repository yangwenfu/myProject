package com.xinyunlian.jinfu.overdue.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.overdue.dto.OverdueMonthDetailDto;
import com.xinyunlian.jinfu.overdue.dto.OverdueMonthPreviewDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Willwang on 2017/1/11.
 */
public interface LoanOverdueService {
    /**
     * 等额本息逾期情况
     * @param loanId
     * @return
     * @throws BizServiceException
     */
    List<OverdueMonthDetailDto> month(String loanId) throws BizServiceException;

    /**
     * 等额本息逾期还款预览
     * @param loanId 贷款编号
     * @param amt 还款金额
     * @param date 还款日 可为空，为空默认为当前日期
     * @return
     * @throws BizServiceException
     */
    List<OverdueMonthPreviewDto> monthPreview(String loanId, BigDecimal amt, Date date) throws BizServiceException;

    /**
     * 逾期还清全部的金额获取
     * @param loanId
     * @return
     * @throws BizServiceException
     */
    BigDecimal all(String loanId) throws BizServiceException;

    /**
     * 获取所有逾期的贷款记录
     * @param currentPage
     * @param pageSize
     * @return
     */
    List<LoanDtlDto> listOverdues(int currentPage, int pageSize);
}
