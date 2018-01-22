package com.xinyunlian.jinfu.service.toDto;

import com.xinyunlian.jinfu.dto.PayRecvReqDto;
import com.xinyunlian.jinfu.dto.PayRecvResultDto;
import com.xinyunlian.jinfu.enums.PayRecvResult;

/**
 * Created by cong on 2016/5/24.
 */
public interface PayRecvToDTOService {

    PayRecvResultDto payToDTO(PayRecvReqDto payReq);

    PayRecvResultDto batchPayToDTO(PayRecvReqDto payReq);

    PayRecvResultDto receiveToDTO(PayRecvReqDto recvReq);
}
