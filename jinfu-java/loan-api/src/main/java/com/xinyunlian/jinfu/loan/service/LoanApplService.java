package com.xinyunlian.jinfu.loan.service;

import com.xinyunlian.jinfu.appl.dto.BeforeTrialDetailDto;
import com.xinyunlian.jinfu.common.dto.BasePagingDto;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.loan.dto.req.LoanApplListDto;
import com.xinyunlian.jinfu.loan.dto.req.LoanCustomerApplDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanApplDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.dto.resp.apply.LoanApplyDetailCusDto;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.schedule.enums.ELoanCustomerStatus;

public interface LoanApplService {

    void save(LoanApplDto loanApplDto);

    LoanApplDto get(String applId);

    LoanProductDetailDto getProduct(String applId);

    /**
     * 初审进件详情(后台)
     * @param applId
     * @return
     */
    BeforeTrialDetailDto getBeforeTrialDetail(String applId);

    /**
     * 申请贷款
     * @param applDto
     */
    LoanApplDto apply(String userId, LoanCustomerApplDto applDto) throws BizServiceException;

    /**
     * 贷款申请重新发起
     */
    LoanApplDto restart(String userId, String applId) throws BizServiceException;

    /**
     * 贷款申请列表
     * @param userId
     * @return
     */
    LoanApplListDto list(String userId, BasePagingDto basePagingDto);

    /**
     * 贷款申请详情
     * @param applId
     * @return
     */
    LoanApplyDetailCusDto detail(String userId, String applId) throws BizServiceException;

    ELoanCustomerStatus getStatus(LoanDtlDto loanDtlDto, LoanApplDto loanApplDto);

    /**
     * 用户是否可以发起贷款
     *
     * @param userId 用户编号
     * @return
     */
    void checkStart(String userId, LoanCustomerApplDto applDto) throws BizServiceException;

    /**
     * 更新签约状态
     *
     * @param applyId
     */
    void updateSigned(String applyId);

    /**
     * 关闭长期未使用的贷款申请
     */
    void closeLongtimeNoused();

    /**
     * 关闭审核通过十天还未使用的小烟贷
     */
    void closeAvgCapAvgIntrTenDaysNoUsed();

    /**
     * 为BTest所提供的贷款申请自动审批路径
     */
    LoanApplDto bTestStart(String userId, LoanCustomerApplDto loanCustomerApplDto) throws BizServiceException;

    /**
     * 获取风控评分
     * @param loanReport
     * @throws BizServiceException
     */
    void getLoanReport(String loanReport) throws BizServiceException;
}