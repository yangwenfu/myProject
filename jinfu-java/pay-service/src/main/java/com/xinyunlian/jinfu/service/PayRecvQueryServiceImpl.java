package com.xinyunlian.jinfu.service;

import com.xinyunlian.jinfu.domain.req.PayRecvResultQueryRequest;
import com.xinyunlian.jinfu.domain.resp.PayRecvResultQueryResponse;
import com.xinyunlian.jinfu.enums.ETranType;
import com.xinyunlian.jinfu.enums.PayRecvResult;
import org.springframework.stereotype.Service;

/**
 * Created by JL on 2016/9/22.
 */
@Service
public class PayRecvQueryServiceImpl implements PayRecvQueryService {
    @Override
    public PayRecvResult payQuery(String bizId, String tranDate) {
        return query(bizId, tranDate, ETranType.PAY);
    }

    @Override
    public PayRecvResult batchPayQuery(String bizId, String tranDate) {
        return query(bizId, tranDate, ETranType.BATCH_PAY);
    }

    @Override
    public PayRecvResult recvQuery(String bizId, String tranDate) {
        return query(bizId, tranDate, ETranType.RECV);
    }


    private PayRecvResult query(String tranNo, String tranDate, ETranType tranType) {
        PayRecvResultQueryRequest request = new PayRecvResultQueryRequest();
        request.setTranType(tranType.getCode());
        request.setTranNo(tranNo);
        request.setTranDate(tranDate.replaceAll("-", ""));
        PayRecvResultQueryResponse response = request.execute();
        return response.getResult();
    }
}
