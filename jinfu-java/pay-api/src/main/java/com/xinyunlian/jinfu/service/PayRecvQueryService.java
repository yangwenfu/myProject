package com.xinyunlian.jinfu.service;

import com.xinyunlian.jinfu.enums.PayRecvResult;

/**
 * Created by JL on 2016/9/22.
 */
public interface PayRecvQueryService {

    PayRecvResult payQuery(String bizId, String tranDate);

    PayRecvResult batchPayQuery(String bizId, String tranDate);

    PayRecvResult recvQuery(String bizId, String tranDate);

}
