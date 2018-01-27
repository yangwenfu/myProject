package com.xinyunlian.jinfu.external.service;

import com.xinyunlian.jinfu.external.dto.*;
import com.xinyunlian.jinfu.external.dto.req.LoanApplyReq;
import com.xinyunlian.jinfu.external.dto.req.RegisterReq;
import com.xinyunlian.jinfu.external.enums.ConfirmationType;
import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;
import com.xinyunlian.jinfu.schedule.dto.ScheduleDto;

import java.util.List;

/**
 * Created by godslhand on 2017/6/16.
 */
public interface ExternalService {


    /**
     * 爱投资贷款申请接口
     *
     * @param loanApply
     * @return
     */
    String loanApply(ExternalLoanApplyDto externalLoanApplyDto);

    /**
     * 确认贷款接口
     * @param loanId
     * @param confirmationType
     * @return
     */
    boolean loanContractConfirm(String loanId, ConfirmationType confirmationType);

    /**
     * 查看逾期接口
     * @param applyId
     * @return
     */
    List<ScheduleDto> getOverDueLoanDetailInfo(String applyId);

    /**
     * 提前还款申请
     * @param applyId 贷款编号
     * @return
     */
    RefundsAdvanceDto loanRefundInAdvance(String applyId);

    /**
     * 提前还款确
     * @param applyId 内容部贷款申請编号
     * @param confirmationType
     * @return
     */
    boolean loanRefuandInAdvanceConfirm(String applyId,  ConfirmationType confirmationType);

    /**
     * 查询最新的还款计划
     * @param applId
     * @return
     */
    List<ScheduleDto> getLoanPaymentPlan(String applId);


    EFinanceSourceType getCode();




}
