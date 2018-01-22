package com.xinyunlian.jinfu.report.dealer.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;

/**
 * Created by menglei on 2016年08月31日.
 */
public class DealerStatsMonthSearchDto extends PagingDto<DealerStatsMonthDto> {

    private static final long serialVersionUID = 1L;

    private String userId;

    private String dealerId;

    private String lastId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getLastId() {
        return lastId;
    }

    public void setLastId(String lastId) {
        this.lastId = lastId;
    }
}
