package com.xinyunlian.jinfu.overdue.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.overdue.dto.OverdueMonthDetailDto;
import com.xinyunlian.jinfu.overdue.dto.OverdueMonthPreviewDto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Willwang on 2017/1/11.
 */
public interface InnerLoanOverdueService {
    /**
     * 等额本息逾期情况
     * @param loanId
     * @return
     * @throws BizServiceException
     */
    List<OverdueMonthDetailDto> month(String loanId) throws BizServiceException;

    /**
     * 等额本息逾期还款预览
     * @param loanId
     * @param amt
     * @param date
     * @return
     * @throws BizServiceException
     */
    List<OverdueMonthPreviewDto> monthPreview(String loanId, BigDecimal amt, Date date) throws BizServiceException;
}
