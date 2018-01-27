package com.ylfin.wallet.portal.controller.req;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StoreExtInfoReq {

    private String relationship;

    private BigDecimal businessArea;

    private Integer employeeNum;

    private String householdRegisterMainPic;

    private String householdRegisterOwnerPic;
}
