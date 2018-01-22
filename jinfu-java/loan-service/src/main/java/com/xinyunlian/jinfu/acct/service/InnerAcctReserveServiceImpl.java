package com.xinyunlian.jinfu.acct.service;

import com.xinyunlian.jinfu.acct.dao.AcctDao;
import com.xinyunlian.jinfu.acct.dao.CreditLineRsrvDtlDao;
import com.xinyunlian.jinfu.acct.dto.req.AcctReserveReq;
import com.xinyunlian.jinfu.acct.entity.AcctPo;
import com.xinyunlian.jinfu.acct.entity.CreditLineRsrvDtlPo;
import com.xinyunlian.jinfu.acct.enums.ERervStatus;
import com.xinyunlian.jinfu.common.constant.ApplicationConstant;
import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.AmtUtils;
import com.xinyunlian.jinfu.common.util.DateHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * @author willwang
 */
@Service
public class InnerAcctReserveServiceImpl implements InnerAcctReserveService {

    @Autowired
    private AcctDao acctDao;

    @Autowired
    private CreditLineRsrvDtlDao creditLineRsrvDtlDao;

    /**
     * 保留
     * @param reserveReq
     * @return
     */
    @Transactional
    public String reserve(AcctReserveReq reserveReq) {
        String reservId = saveCreditLineReserveDtl(reserveReq);
        saveCreditLineReserve(reserveReq);
        return reservId;
    }

    /**
     * 解保留
     * @param reservId
     */
    @Transactional
    public void unReserve(String reservId) {
        CreditLineRsrvDtlPo reserveDetail = closeReserveDetail(reservId);
        saveCreditLineReserve(reserveDetail, false);
    }

    @Override
    @Transactional
    public void reserve(String reservId) {
        CreditLineRsrvDtlPo reserveDetail = openReserveDetail(reservId);
        saveCreditLineReserve(reserveDetail, true);
    }

    @Override
    public void useCreditLine(String acctNo, BigDecimal amt) {
        AcctPo acctPo = acctDao.findOne(acctNo);
        BigDecimal used = acctPo.getCreditLineUsed().add(amt);
        acctPo.setCreditLineUsed(used);
        acctDao.save(acctPo);
    }

    @Override
    @Transactional
    public void releaseCreditLine(String acctNo, BigDecimal amt) {
        AcctPo acctPo = acctDao.findOne(acctNo);
        BigDecimal used = acctPo.getCreditLineUsed().subtract(amt);
        used = used.compareTo(BigDecimal.ZERO) > 0 ? used : BigDecimal.ZERO;
        acctPo.setCreditLineUsed(AmtUtils.processNegativeAmt(used));
        acctDao.save(acctPo);
    }

    private void saveCreditLineReserve(AcctReserveReq reserveReq) {
        AcctPo po = acctDao.findOne(reserveReq.getAcctNo());
        if(po == null){
            return;
        }
        BigDecimal creditLineRsrv = po.getCreditLineRsrv().add(reserveReq.getCreditLineRsrv());
        po.setCreditLineRsrv(creditLineRsrv);
        acctDao.save(po);
    }

    private String saveCreditLineReserveDtl(AcctReserveReq reserveReq) {
        CreditLineRsrvDtlPo po;
        if(StringUtils.isNotEmpty(reserveReq.getRsrvId())){
            po = creditLineRsrvDtlDao.findOne(reserveReq.getRsrvId());
            ConverterService.convert(reserveReq, po);
        }else{
            po = ConverterService.convert(reserveReq, CreditLineRsrvDtlPo.class);
        }

        if(po != null){
            po.setEffDate(DateHelper.getWorkDate());
            po.setExpDate(ApplicationConstant.MAX_DATE);
            po.setStatus(ERervStatus.ACTIVATE);
            po = creditLineRsrvDtlDao.save(po);
            return po.getRsrvId();
        }

        return null;
    }

    private CreditLineRsrvDtlPo closeReserveDetail(String reservId) {
        CreditLineRsrvDtlPo po = creditLineRsrvDtlDao.findOne(reservId);
        if(po != null){
            po.setStatus(ERervStatus.CLOSE);
            po.setExpDate(DateHelper.getWorkDate());
            creditLineRsrvDtlDao.save(po);
        }
        return po;
    }

    private CreditLineRsrvDtlPo openReserveDetail(String reservId){
        CreditLineRsrvDtlPo po = creditLineRsrvDtlDao.findOne(reservId);
        if(po != null){
            po.setStatus(ERervStatus.ACTIVATE);
            po.setExpDate(DateHelper.getWorkDate());
            creditLineRsrvDtlDao.save(po);
        }
        return po;
    }

    private void saveCreditLineReserve(CreditLineRsrvDtlPo reserveDetail, Boolean isAdded) {
        if(reserveDetail == null){
            return;
        }
        AcctPo po = acctDao.findOne(reserveDetail.getAcctNo());
        BigDecimal creditLineRsrv = BigDecimal.ZERO;
        if(isAdded){
            creditLineRsrv = po.getCreditLineRsrv().add(reserveDetail.getCreditLineRsrv());
        }else{
            creditLineRsrv = AmtUtils.max(po.getCreditLineRsrv().subtract(reserveDetail.getCreditLineRsrv()), BigDecimal.ZERO);
        }
        po.setCreditLineRsrv(creditLineRsrv);
        acctDao.save(po);
    }

}
