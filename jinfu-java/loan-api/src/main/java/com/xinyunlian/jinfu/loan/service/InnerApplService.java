package com.xinyunlian.jinfu.loan.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.dto.resp.apply.LoanApplyDetailCusDto;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.router.dto.FinanceSourceDto;
import com.xinyunlian.jinfu.schedule.enums.ELoanCustomerStatus;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author willwang
 */
public interface InnerApplService {

    void save(LoanApplDto loanApplDto);

    LoanApplDto get(String applyId);

    LoanProductDetailDto getProduct(String applyId);

    ELoanCustomerStatus getStatus(LoanDtlDto loan, LoanApplDto apply);

    /**
     * 用户端的状态聚合查询
     * @param loan
     * @param apply
     * @param financeSourceDtoMap
     * @return
     */
    ELoanCustomerStatus getStatus(LoanDtlDto loan, LoanApplDto apply, Map<Integer, FinanceSourceDto> financeSourceDtoMap) throws BizServiceException;

    LoanDtlDto getLoan(String applId);

    /**
     * 获取贷款详情
     * @param userId
     * @param applId
     * @return
     */
    LoanApplyDetailCusDto detail(String userId, String applId) throws BizServiceException;
}
