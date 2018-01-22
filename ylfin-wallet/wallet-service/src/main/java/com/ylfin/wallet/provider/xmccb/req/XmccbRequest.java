package com.ylfin.wallet.provider.xmccb.req;

import lombok.Data;

@Data
public class XmccbRequest {

    private String serviceName;

    private String platformNo;

    private String reqData;

    private String keySerial;

    private String sign;
}
