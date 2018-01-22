package com.xinyunlian.jinfu.carbank.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;

/**
 * Created by dongfangchao on 2017/7/18/0018.
 */
public class LoanCarbankOrderSearchDto extends PagingDto<LoanCarbankOrderDto> {
    private static final long serialVersionUID = -8545791541462647126L;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
