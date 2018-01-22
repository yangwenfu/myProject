package com.ylfin.wallet.provider.xmccb.req;

import lombok.Data;

@Data
public class OpenAccountReq extends AbstractIdempotentReq {

    private String requestNo;

    private String realName;

    private String idCardType;

    private String userRole;

    private String idCardNo;

    private String mobile;

    private String bankcardNo;

    private String checkType;

    private String userLimitType;

    private String authList;
}
