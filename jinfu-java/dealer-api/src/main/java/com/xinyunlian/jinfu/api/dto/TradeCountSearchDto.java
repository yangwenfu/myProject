package com.xinyunlian.jinfu.api.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;

/**
 * Created by menglei on 2016年09月26日.
 */
public class TradeCountSearchDto extends PagingDto<ApiTradeCountDto.TradeCount> {

    private static final long serialVersionUID = 1L;

    private String storeId;

    private String datebegin;

    private String dateend;

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getDatebegin() {
        return datebegin;
    }

    public void setDatebegin(String datebegin) {
        this.datebegin = datebegin;
    }

    public String getDateend() {
        return dateend;
    }

    public void setDateend(String dateend) {
        this.dateend = dateend;
    }
}
