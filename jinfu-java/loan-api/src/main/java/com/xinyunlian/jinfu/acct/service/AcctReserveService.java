package com.xinyunlian.jinfu.acct.service;

import com.xinyunlian.jinfu.acct.dto.req.AcctReserveReq;

import java.math.BigDecimal;

/**
 * 保留的意思相当于一个占用额度的记录
 * @author willwang
 */
public interface AcctReserveService {

    /**
     * 保留
     *
     * @param reserveReq
     * @return
     */
    String reserve(AcctReserveReq reserveReq);

    /**
     * 解保留
     *
     * @param reservId
     */
    void unReserve(String reservId);

    void useCreditLine(String acctNo, BigDecimal amt);

    void releaseCreditLine(String acctNo, BigDecimal amt);

    /**
     * 获取用户可贷的额度
     * @param userId
     * @param productId
     * @return
     */
    BigDecimal getCan(String userId, String productId);

}
