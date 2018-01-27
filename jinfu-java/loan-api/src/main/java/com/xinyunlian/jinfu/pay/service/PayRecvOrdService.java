package com.xinyunlian.jinfu.pay.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.pay.dto.PayRecvOrdDto;
import com.xinyunlian.jinfu.pay.enums.EOrdStatus;
import com.xinyunlian.jinfu.pay.enums.EPrType;

import java.util.List;

/**
 * @author willwang
 */
public interface PayRecvOrdService {

    PayRecvOrdDto save(PayRecvOrdDto payRecvOrdDto);

    PayRecvOrdDto findByBizId(String bizId);

    List<PayRecvOrdDto> findByUserId(String userId) throws BizServiceException;

    PayRecvOrdDto get(String ordId);

    List<PayRecvOrdDto> findByOrdStatusAndPrType(EOrdStatus ordStatus, EPrType prType);

}
