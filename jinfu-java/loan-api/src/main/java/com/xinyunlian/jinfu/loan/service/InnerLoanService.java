package com.xinyunlian.jinfu.loan.service;

import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;

/**
 * @author willwang
 */
public interface InnerLoanService {

    /**
     * 贷款信息变更
     *
     * @param loanDtlDto
     */
    void save(LoanDtlDto loanDtlDto);

    /**
     * 获取贷款详情
     *
     * @param loanId
     * @return
     */
    LoanDtlDto get(String loanId);

}
