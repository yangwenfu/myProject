package com.ylfin.wallet.portal.controller.vo;

import lombok.Data;

@Data
public class StoreInfo {

    private Long storeId;

    // 行业
    private String industryMcc;

    private String storeName;

    private Long provinceId;

    private Long cityId;

    private Long areaId;

    private Long streetId;

    private String address;

    private String tel;

    private Double businessArea;

    private Integer employeeNum;
}
