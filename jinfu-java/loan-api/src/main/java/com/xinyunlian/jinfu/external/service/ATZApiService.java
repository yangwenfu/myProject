package com.xinyunlian.jinfu.external.service;

import com.xinyunlian.jinfu.external.dto.OverDueLoanDtl;
import com.xinyunlian.jinfu.external.dto.RefundsAdvanceDto;
import com.xinyunlian.jinfu.external.dto.RefundDto;
import com.xinyunlian.jinfu.external.dto.RequsetInfo;
import com.xinyunlian.jinfu.external.dto.req.LoanApplyReq;
import com.xinyunlian.jinfu.external.dto.req.RegisterReq;
import com.xinyunlian.jinfu.external.enums.ConfirmationType;

import java.util.List;

/**
 * Created by godslhand on 2017/6/17.
 */
public interface ATZApiService {

    /**
     * 爱投资开户接口额
     *
     * @param registerReq
     */
    void register(RegisterReq registerReq);

    /**
     * 爱投资贷款申请接口
     *
     * @param loanApply
     * @return
     */
    String loanApply(LoanApplyReq loanApply);

    /**
     * 确认贷款接口
     *
     * @param loanId
     * @param confirmationType
     * @return
     */
    boolean loanContractConfirm(String loanId, ConfirmationType confirmationType);

    /**
     * 查看逾期接口
     *
     * @param loanId
     * @return
     */
    OverDueLoanDtl getOverDueLoanDetailInfo(String loanId);

    /**
     * 提前还款申请
     *
     * @param loanId 贷款编号
     * @return
     */
    RefundsAdvanceDto loanRefundInAdvance(String loanId);

    /**
     * 提前还款确认编号
     * @param loanId 贷款编号
     * @param loanRefuandInAdvanceId 提前还款交易号
     * @param confirmationType
     * @return
     */
    boolean loanRefuandInAdvanceConfirm(String loanId, String loanRefuandInAdvanceId, ConfirmationType confirmationType);

    /**
     * 查询最新的还款计划
     * @param loanId
     * @return
     */
   List<RefundDto> getLoanPaymentPlan(String loanId);

    /**
     * 回调参数签名
     * @return {"method":"register","ver":"2.0","channelId":"99","params":{"idNo":"21010219910405503X"},"signType":"MD5","sign":"0bfe06f21df066a1d1bf92d4fb0285aa","statusCode":200}
     */
    String signResponese(RequsetInfo requsetInfo );

    /**
     *
     * @param request
     * @return
     */
     boolean checkSign(String request);
}
