package com.xinyunlian.jinfu.acct.service;

import com.xinyunlian.jinfu.acct.dao.AcctDao;
import com.xinyunlian.jinfu.acct.dto.req.AcctReserveReq;
import com.xinyunlian.jinfu.acct.entity.AcctPo;
import com.xinyunlian.jinfu.common.util.AmtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author willwang
 */
@Service
public class AcctReserveServiceImpl implements AcctReserveService {

    @Autowired
    private InnerAcctReserveService innerAcctReserveService;

    @Autowired
    private AcctDao acctDao;

    /**
     * 保留
     * @param reserveReq
     * @return
     */
    @Transactional
    public String reserve(AcctReserveReq reserveReq) {
        return innerAcctReserveService.reserve(reserveReq);
    }

    /**
     * 解保留
     * @param reservId
     */
    @Transactional
    public void unReserve(String reservId) {
        innerAcctReserveService.unReserve(reservId);
    }

    @Override
    public void useCreditLine(String acctNo, BigDecimal amt) {
        innerAcctReserveService.useCreditLine(acctNo, amt);
    }

    @Override
    @Transactional
    public void releaseCreditLine(String acctNo, BigDecimal amt) {
        innerAcctReserveService.releaseCreditLine(acctNo, amt);
    }

    @Override
    public BigDecimal getCan(String userId, String productId) {
        AcctPo acctPo = acctDao.findByUserIdAndProductId(userId, productId);

        if(acctPo == null){
            return null;
        }

        BigDecimal can = acctPo.getCreditLine().subtract(acctPo.getCreditLineUsed()).subtract(acctPo.getCreditLineRsrv());

        return AmtUtils.max(can, BigDecimal.ZERO);
    }
}
