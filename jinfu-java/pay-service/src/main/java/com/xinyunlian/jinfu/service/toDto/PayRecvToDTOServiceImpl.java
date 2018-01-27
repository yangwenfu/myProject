package com.xinyunlian.jinfu.service.toDto;

import com.xinyunlian.jinfu.dto.PayRecvReqDto;
import com.xinyunlian.jinfu.dto.PayRecvResultDto;
import com.xinyunlian.jinfu.enums.PayRecvResult;
import org.springframework.stereotype.Service;

/**
 * Created by JL on 2016/9/12.
 */
@Service
public class PayRecvToDTOServiceImpl extends com.xinyunlian.jinfu.service.PayRecvServiceImpl implements PayRecvToDTOService {
    @Override
    public PayRecvResultDto payToDTO(PayRecvReqDto payReq) {
        PayRecvResult payRecvResul = super.pay(payReq);
        return new PayRecvResultDto(payRecvResul, payRecvResul.getRetCode(), payRecvResul.getRetMsg());
    }

    @Override
    public PayRecvResultDto batchPayToDTO(PayRecvReqDto payReq) {
        PayRecvResult payRecvResul = super.batchPay(payReq);
        return new PayRecvResultDto(payRecvResul, payRecvResul.getRetCode(), payRecvResul.getRetMsg());
    }

    @Override
    public PayRecvResultDto receiveToDTO(PayRecvReqDto recvReq) {
        PayRecvResult payRecvResul = super.receive(recvReq);
        return new PayRecvResultDto(payRecvResul, payRecvResul.getRetCode(), payRecvResul.getRetMsg());
    }
}
