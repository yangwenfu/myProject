package com.xinyunlian.jinfu.pay.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.pay.dao.PayRecvOrdDao;
import com.xinyunlian.jinfu.pay.dto.PayRecvOrdDto;
import com.xinyunlian.jinfu.pay.entity.PayRecvOrdPo;
import com.xinyunlian.jinfu.pay.enums.EOrdStatus;
import com.xinyunlian.jinfu.pay.enums.EPrType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author willwang
 */
@Service
public class PayRecvOrdServiceImpl implements PayRecvOrdService{

    @Autowired
    private InnerPayRecvOrdService innerPayRecvOrdService;

    @Autowired
    private PayRecvOrdDao payRecvOrdDao;

    @Override
    @Transactional
    public PayRecvOrdDto save(PayRecvOrdDto payRecvOrdDto) {
        return innerPayRecvOrdService.save(payRecvOrdDto);
    }

    @Override
    public PayRecvOrdDto findByBizId(String bizId) {
        return innerPayRecvOrdService.findByBizId(bizId);
    }

    @Override
    public List<PayRecvOrdDto> findByUserId(String userId) throws BizServiceException {
        List<PayRecvOrdPo> payRecvOrdPos = payRecvOrdDao.findByUserId(userId);
        return ConverterService.convertToList(payRecvOrdPos, PayRecvOrdDto.class);
    }

    @Override
    public PayRecvOrdDto get(String ordId) {
        PayRecvOrdPo payRecvOrdPo = payRecvOrdDao.findByOrdId(ordId);
        return ConverterService.convert(payRecvOrdPo, PayRecvOrdDto.class);
    }

    @Override
    public List<PayRecvOrdDto> findByOrdStatusAndPrType(EOrdStatus ordStatus, EPrType prType) {
        return innerPayRecvOrdService.findByOrdStatusAndPrType(ordStatus, prType);
    }
}
