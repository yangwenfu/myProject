package com.ylfin.wallet.provider.xmccb.req;

import lombok.Data;

@Data
public abstract class AbstractIdempotentReq {

    private String requestNo;
}
