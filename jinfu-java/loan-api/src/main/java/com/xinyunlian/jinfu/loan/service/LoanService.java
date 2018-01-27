package com.xinyunlian.jinfu.loan.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.loan.dto.req.LoanPayReq;
import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.pay.dto.PayRecvOrdDto;
import com.xinyunlian.jinfu.product.dto.LoanProductDetailDto;
import com.xinyunlian.jinfu.promo.dto.PromoDto;

import java.util.Collection;
import java.util.List;

/**
 * @author willwang
 */
public interface LoanService {

    /**
     * 同意贷款后，会返回贷款编号
     *
     * @param userId 用户编号
     * @param applId 申请编号
     * @param cardId 银行卡编号
     * @return
     * @throws BizServiceException
     */
    LoanDtlDto agree(String userId, String applId, Long cardId, PromoDto promoDto, LoanProductDetailDto product) throws BizServiceException;

    /**
     * 获取贷款详情
     *
     * @param loanId
     * @return
     */
    LoanDtlDto get(String loanId);

    /**
     * 贷款信息变更
     *
     * @param loanDtlDto
     */
    void save(LoanDtlDto loanDtlDto);

    /**
     * 根据applId做放款成功之后的状态变更
     * @param applId
     */
    void updateTransferSuccessByApplId(String applId);

    LoanDtlDto payCallback(LoanDtlDto loanDtlDto, PayRecvOrdDto payRecvOrdDto);

    /**
     * 获得用户实际发生的贷款数量
     * @param userId
     * @return
     */
    Long count(String userId);

    /**
     * 获得用户某个产品实际发生的贷款数量
     * @param userId
     * @return
     */
    Long count(String userId, String prodId);

    /**
     * 付款
     */
    PayRecvOrdDto pay(LoanPayReq loanPayReq);

    /**
     * 发送放款成功后的短信
     */
    void sendPayOkMessage(LoanPayReq loanPayReq, LoanDtlDto loanDtlDto);

    void sendPayOkMessage(LoanDtlDto loanDtlDto, String bankCardNo, String moile);

    /**
     * 根据申请单编号找到一堆贷款信息
     * @param applIds
     * @return
     */
    List<LoanDtlDto> findByApplIds(Collection<String> applIds);

    /**
     * 查询银行卡是否存在贷款
     * @param bankCardId
     * @return
     */
    List<LoanDtlDto> findByBankCardId(Long bankCardId);

}
