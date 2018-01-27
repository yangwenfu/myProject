package com.xinyunlian.jinfu.pay.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.loan.dto.repay.LinkRepayDto;
import com.xinyunlian.jinfu.loan.entity.RepayDtlPo;
import com.xinyunlian.jinfu.loan.enums.ERepayStatus;
import com.xinyunlian.jinfu.loan.service.InnerLinkRepayService;
import com.xinyunlian.jinfu.pay.dao.PayRecvOrdDao;
import com.xinyunlian.jinfu.pay.dto.PayRecvOrdDto;
import com.xinyunlian.jinfu.pay.entity.PayRecvOrdPo;
import com.xinyunlian.jinfu.pay.enums.EOrdStatus;
import com.xinyunlian.jinfu.pay.enums.EPrType;
import com.xinyunlian.jinfu.repay.dao.RepayDao;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author willwang
 */
@Service
public class InnerPayRecvOrdServiceImpl implements InnerPayRecvOrdService {

    @Autowired
    private PayRecvOrdDao payRecvOrdDao;

    @Autowired
    private InnerLinkRepayService innerLinkRepayService;

    @Autowired
    private RepayDao repayDao;

    @Override
    @Transactional
    public PayRecvOrdDto save(PayRecvOrdDto payRecvOrdDto) {
        PayRecvOrdPo po;
        if (payRecvOrdDto.getOrdId() == null) {
            po = new PayRecvOrdPo();
        } else {
            po = payRecvOrdDao.findOne(payRecvOrdDto.getOrdId());
        }

        if (po.getCreateTs() != null) {
            po.setVersionCt(0L);
        }
        ConverterService.convert(payRecvOrdDto, po);
        payRecvOrdDao.save(po);
        //id回传
        payRecvOrdDto.setOrdId(po.getOrdId());
        return payRecvOrdDto;
    }

    @Override
    public PayRecvOrdDto findByBizId(String bizId) {
        List<PayRecvOrdPo> payRecvOrdPos = payRecvOrdDao.findByBizId(bizId);
        if(CollectionUtils.isEmpty(payRecvOrdPos)){ return null;}
        return ConverterService.convert(payRecvOrdPos.get(0), PayRecvOrdDto.class);
    }

    @Override
    public void canRepay(String loanId) throws BizServiceException{
        List<RepayDtlPo> repays = repayDao.getRepayedList(loanId);
        if(CollectionUtils.isEmpty(repays)){ return;}
        for (RepayDtlPo repay : repays) {
            if(Arrays.asList(ERepayStatus.PROCESS, ERepayStatus.PAY).contains(repay.getStatus())){
                throw new BizServiceException(EErrorCode.LOAN_CANT_RETRY_REPAY,
                    String.format("%s 有处理中的还款记录，无法还款", loanId));
            }
        }
    }

    @Override
    public List<PayRecvOrdDto> findByBizIds(List<String> bizIds) {
        List<PayRecvOrdDto> rs = new ArrayList<>();
        if(CollectionUtils.isEmpty(bizIds)){ return rs;}
        List<PayRecvOrdPo> list = payRecvOrdDao.findByBizIdIn(bizIds);
        for (PayRecvOrdPo payRecvOrdPo : list) {
            PayRecvOrdDto dto = ConverterService.convert(payRecvOrdPo, PayRecvOrdDto.class);
            dto.setCreateDateTime(payRecvOrdPo.getCreateTs());
            rs.add(dto);
        }
        return rs;
    }

    @Override
    public List<PayRecvOrdDto> findByOrdStatusAndPrType(EOrdStatus ordStatus, EPrType prType) {
        List<PayRecvOrdPo> payRecvOrdPos = payRecvOrdDao.findByOrdStatusAndPrType(ordStatus, prType);

        List<PayRecvOrdDto> list = new ArrayList<>();

        for (PayRecvOrdPo payRecvOrdPo : payRecvOrdPos) {
            list.add(ConverterService.convert(payRecvOrdPo, PayRecvOrdDto.class));
        }

        return list;
    }
}
