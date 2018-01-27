package com.xinyunlian.jinfu.carbank.api.dto.request;

import com.xinyunlian.jinfu.carbank.api.dto.response.OrderStatusResponse;

/**
 * Created by dongfangchao on 2017/7/18/0018.
 */
public class OrderStatusRequest extends CarBankTemplate<OrderStatusResponse>{

    private static final String url = "/resource/fcar/partner/orderStatus";

    private String loanApplyNo;

    public String getLoanApplyNo() {
        return loanApplyNo;
    }

    public void setLoanApplyNo(String loanApplyNo) {
        this.loanApplyNo = loanApplyNo;
    }

    @Override
    public String getRequestUrl() {
        return url;
    }
}
