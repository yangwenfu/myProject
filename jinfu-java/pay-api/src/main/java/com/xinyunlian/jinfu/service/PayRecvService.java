package com.xinyunlian.jinfu.service;

import com.xinyunlian.jinfu.dto.PayRecvReqDto;
import com.xinyunlian.jinfu.enums.PayRecvResult;

/**
 * Created by cong on 2016/5/24.
 */
public interface PayRecvService {

    PayRecvResult pay(PayRecvReqDto payReq);

    PayRecvResult batchPay(PayRecvReqDto payReq);

    PayRecvResult receive(PayRecvReqDto recvReq);
}
