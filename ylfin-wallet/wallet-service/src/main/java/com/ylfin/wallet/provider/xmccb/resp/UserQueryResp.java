package com.ylfin.wallet.provider.xmccb.resp;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserQueryResp {

    private String code;

    private String description;

    private String platformUserNo;

    private String userType;

    private String userRole;

    private String auditStatus;

    private String activeStatus;

    private BigDecimal balance;

    private BigDecimal availableAmount;

    private BigDecimal freezeAmount;

    private BigDecimal floatBalance;

    private BigDecimal arriveBalance;

    private String bankcardNo;

    private String bankcode;

    private String mobile;

    private String authlist;

    private boolean isImportUserActivate;

    private String accessType;

    private String idCardType;

    private String idCardNo;

    private String name;

    private String virtualSubAccount;
}
