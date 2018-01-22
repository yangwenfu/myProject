package com.xinyunlian.jinfu.api.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;

/**
 * Created by menglei on 2016年09月26日.
 */
public class TradeSearchDto extends PagingDto<ApiTradeDto.Trade> {

    private static final long serialVersionUID = 1L;

    private String storeId;

    private String date;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
