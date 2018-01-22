package com.xinyunlian.jinfu.acct.service;

import com.xinyunlian.jinfu.acct.dto.req.AcctReserveReq;

import java.math.BigDecimal;

/**
 *
 * @author willwang
 */
public interface InnerAcctReserveService {

    /**
     * 保留
     *
     * @param reserveReq
     * @return
     */
    String reserve(AcctReserveReq reserveReq);

    /**
     * 保留
     */
    void reserve(String reservId);

    /**
     * 解保留
     *
     * @param reservId
     */
    void unReserve(String reservId);

    void useCreditLine(String acctNo, BigDecimal amt);

    void releaseCreditLine(String acctNo, BigDecimal amt);

}
