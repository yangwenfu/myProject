package com.xinyunlian.jinfu.pay.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.pay.dto.PayRecvOrdDto;
import com.xinyunlian.jinfu.pay.enums.EOrdStatus;
import com.xinyunlian.jinfu.pay.enums.EPrType;

import java.util.List;

/**
 * @author willwang
 */
public interface InnerPayRecvOrdService {

    PayRecvOrdDto save(PayRecvOrdDto payRecvOrdDto);

    PayRecvOrdDto findByBizId(String bizId);


    void canRepay(String loanId) throws BizServiceException;


    List<PayRecvOrdDto> findByBizIds(List<String> bizIds);


    List<PayRecvOrdDto> findByOrdStatusAndPrType(EOrdStatus ordStatus, EPrType prType);

}
