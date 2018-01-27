package com.xinyunlian.jinfu.repay.service;

import com.xinyunlian.jinfu.calc.dto.LoanCalcDto;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.loan.dto.RepayDtlDto;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.loan.enums.ERepayType;
import com.xinyunlian.jinfu.pay.dto.PayRecvOrdDto;
import com.xinyunlian.jinfu.pay.enums.EPrType;
import com.xinyunlian.jinfu.repay.dto.ExternalRepayCallbackDto;
import com.xinyunlian.jinfu.repay.dto.LoanRepayRespDto;
import com.xinyunlian.jinfu.repay.dto.RepayReadyDto;
import com.xinyunlian.jinfu.repay.dto.req.RepayReqDto;
import com.xinyunlian.jinfu.repay.dto.resp.LoanCashierCallbackDto;
import com.xinyunlian.jinfu.schedule.dto.ScheduleDto;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * @author willwang
 */
public interface RepayService {

    RepayDtlDto save(RepayDtlDto repayDtlDto);

    RepayDtlDto get(String repayId);

    List<LoanRepayRespDto> list(String loanId);

    List<RepayDtlDto> listByLoanId(String loanId);

    /**
     * 根据一堆贷款编号查询还款记录
     * @param loanIds
     * @return
     */
    List<RepayDtlDto> findByLoanIds(Collection<String> loanIds);

    /**
     * 逾期更新脚本
     */
    void overdueJob();

    /**
     * 逾期自动代扣脚本
     */
    void overdueWithhold();

    /**
     * 预还款(生成repayDtl,payRecv,link等数据)
     * @param userId 还款用户
     * @param request 还款请求
     * @param prType 还款方式
     * @return 还款记录
     */
    RepayDtlDto inAdvanceRepay(String userId, RepayReqDto request, EPrType prType) throws BizServiceException;

    /**
     * 预还款(生成repayDtl,payRecv,link等数据),可接收外部传入的计算结果参数
     * @param userId 还款用户
     * @param request 还款请求
     * @param prType 还款方式
     * @return 还款记录
     */
    RepayDtlDto inAdvanceRepayWithCalc(String userId, RepayReqDto request, EPrType prType, LoanCalcDto loanCalcDto) throws BizServiceException;

    /**
     * 还款计算方法
     * @param userId
     * @param request
     * @return
     */
    LoanCalcDto calc(String userId, RepayReqDto request) throws BizServiceException;

    /**
     * 根据还款方式以及还款记录进行还款结算
     */
    void repay(RepayReqDto request, RepayDtlDto repay, LoanCashierCallbackDto callback, String mobile) throws BizServiceException;

    /**
     * 还款失败处理
     * @param repay
     * @throws BizServiceException
     */
    void repayFailed(RepayDtlDto repay) throws BizServiceException;

    /**
     * 第三方还款发起
     * @param loanId
     * @throws BizServiceException
     */
    void externalInAdvance(String loanId) throws BizServiceException;

    /**
     * 第三方还款结果回调
     */
    void externalRepayCallback(ExternalRepayCallbackDto externalRepayCallbackDto) throws BizServiceException;

    /**
     * 代扣
     * @throws BizServiceException
     */
    void withhold(String userId, EPrType prType, RepayReqDto request) throws BizServiceException;

}
