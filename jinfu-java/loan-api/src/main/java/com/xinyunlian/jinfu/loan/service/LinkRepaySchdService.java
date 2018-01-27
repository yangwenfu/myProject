package com.xinyunlian.jinfu.loan.service;

import com.xinyunlian.jinfu.loan.dto.repay.LinkRepayDto;
import com.xinyunlian.jinfu.loan.dto.req.LoanRepayLinkDto;

import java.util.List;

/**
 * @author Willwang
 */
public interface LinkRepaySchdService {

    void add(String repayId, String scheduleId);

    void add(LoanRepayLinkDto loanRepayLinkDto);

    List<LinkRepayDto> findByRepayId(String repayId);

}
