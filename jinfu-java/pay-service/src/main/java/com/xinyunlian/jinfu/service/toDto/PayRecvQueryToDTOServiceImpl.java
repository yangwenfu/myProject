package com.xinyunlian.jinfu.service.toDto;

import com.xinyunlian.jinfu.dto.PayRecvResultDto;
import com.xinyunlian.jinfu.enums.PayRecvResult;
import org.springframework.stereotype.Service;

/**
 * Created by JL on 2016/9/22.
 */
@Service
public class PayRecvQueryToDTOServiceImpl extends com.xinyunlian.jinfu.service.PayRecvQueryServiceImpl implements PayRecvQueryToDTOService {

    @Override
    public PayRecvResultDto payQueryToDTO(String bizId, String tranDate) {
        PayRecvResult payRecvResul = super.payQuery(bizId, tranDate);
        return new PayRecvResultDto(payRecvResul, payRecvResul.getRetCode(), payRecvResul.getRetMsg());
    }

    @Override
    public PayRecvResultDto batchPayQueryToDTO(String bizId, String tranDate) {
        PayRecvResult payRecvResul = super.batchPayQuery(bizId, tranDate);
        return new PayRecvResultDto(payRecvResul, payRecvResul.getRetCode(), payRecvResul.getRetMsg());
    }

    @Override
    public PayRecvResultDto recvQueryToDTO(String bizId, String tranDate) {
        PayRecvResult payRecvResul = super.recvQuery(bizId, tranDate);
        return new PayRecvResultDto(payRecvResul, payRecvResul.getRetCode(), payRecvResul.getRetMsg());
    }
}
