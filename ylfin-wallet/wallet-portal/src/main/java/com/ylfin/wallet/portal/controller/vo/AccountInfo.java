package com.ylfin.wallet.portal.controller.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountInfo {

    /**
     * 实名认证标识
     */
    private Boolean certification;

    private BigDecimal balance;
}
