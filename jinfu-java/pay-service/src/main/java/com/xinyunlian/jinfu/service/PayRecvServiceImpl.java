package com.xinyunlian.jinfu.service;

import com.xinyunlian.jinfu.common.util.NumberUtil;
import com.xinyunlian.jinfu.domain.req.PayRecvRequest;
import com.xinyunlian.jinfu.domain.resp.PayRecvResponse;
import com.xinyunlian.jinfu.dto.PayRecvReqDto;
import com.xinyunlian.jinfu.enums.ETranType;
import com.xinyunlian.jinfu.enums.PayRecvResult;
import org.springframework.stereotype.Service;

/**
 * Created by JL on 2016/9/12.
 */
@Service
public class PayRecvServiceImpl implements PayRecvService {
    @Override
    public PayRecvResult pay(PayRecvReqDto payReq) {
        PayRecvRequest payRequest = buildRequest(payReq, ETranType.PAY);
        PayRecvResponse payRecvResponse = payRequest.execute();
        return payRecvResponse.getResult();
    }

    @Override
    public PayRecvResult batchPay(PayRecvReqDto payReq) {
        PayRecvRequest payRequest = buildRequest(payReq, ETranType.BATCH_PAY);
        PayRecvResponse payRecvResponse = payRequest.execute();
        return payRecvResponse.getResult();
    }

    @Override
    public PayRecvResult receive(PayRecvReqDto recvReq) {
        PayRecvRequest payRequest = buildRequest(recvReq, ETranType.RECV);
        PayRecvResponse payRecvResponse = payRequest.execute();
        return payRecvResponse.getResult();
    }


    private PayRecvRequest buildRequest(PayRecvReqDto payReq, ETranType tranType) {
        PayRecvRequest payRequest = new PayRecvRequest();
        payRequest.setTranType(tranType.getCode());
        payRequest.setTranAmt(NumberUtil.formatToFen(payReq.getTrxAmt()));
        payRequest.setTranNo(payReq.getTranNo());
        payRequest.setAccNo(payReq.getBankCardNo());
        payRequest.setAccName(payReq.getBankCardName());
        payRequest.setCertifyType("01");
        payRequest.setCertifyNo(payReq.getIdCardNo());
        payRequest.setOrgCode(payReq.getBankCode());
        if (payReq.getToPrivate()) {
            payRequest.setAccType("2");
        } else {
            payRequest.setAccType("1");
        }
        payRequest.setPurpose(payReq.getTrxMemo());
        return payRequest;
    }
}
