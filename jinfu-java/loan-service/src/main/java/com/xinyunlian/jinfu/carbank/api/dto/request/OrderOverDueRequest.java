package com.xinyunlian.jinfu.carbank.api.dto.request;

import com.xinyunlian.jinfu.carbank.api.dto.response.OrderOverDueResponse;

/**
 * Created by dongfangchao on 2017/7/18/0018.
 */
public class OrderOverDueRequest extends CarBankTemplate<OrderOverDueResponse> {

    private static final String url = "/resource/fcar/partner/orderOverDue";

    private String applyNo;

    public String getApplyNo() {
        return applyNo;
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo;
    }

    @Override
    public String getRequestUrl() {
        return url;
    }
}
