package com.xinyunlian.jinfu.uplus.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;

/**
 * Created by menglei on 2017年03月20日.
 */
public class ThirdOrderInfoSearchDto extends PagingDto<ThirdOrderInfoDto> {

    private static final long serialVersionUID = 1L;

    private Long storeId;

    private String orderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }
}
