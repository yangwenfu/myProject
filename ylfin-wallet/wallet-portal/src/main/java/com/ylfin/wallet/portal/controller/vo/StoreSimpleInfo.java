package com.ylfin.wallet.portal.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class StoreSimpleInfo {

    private Long storeId;

    private String storeName;

    private String fullAddress;

    private Date createTs;
}
