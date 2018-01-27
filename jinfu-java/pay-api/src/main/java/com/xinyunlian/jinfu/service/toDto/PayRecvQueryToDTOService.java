package com.xinyunlian.jinfu.service.toDto;

import com.xinyunlian.jinfu.dto.PayRecvResultDto;
import com.xinyunlian.jinfu.enums.PayRecvResult;

/**
 * Created by JL on 2016/9/22.
 */
public interface PayRecvQueryToDTOService {

    PayRecvResultDto payQueryToDTO(String bizId, String tranDate);

    PayRecvResultDto batchPayQueryToDTO(String bizId, String tranDate);

    PayRecvResultDto recvQueryToDTO(String bizId, String tranDate);

}
