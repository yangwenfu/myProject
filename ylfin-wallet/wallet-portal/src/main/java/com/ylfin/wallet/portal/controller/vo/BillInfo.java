package com.ylfin.wallet.portal.controller.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class BillInfo {

    private String title;

    private Date tradeTime;

    private BigDecimal tradeAmount;

    // 收入/支出
    private Integer tradeType;
}
