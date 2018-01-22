package com.xinyunlian.jinfu.thirdparty.nbcb.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bright on 2017/5/17.
 */
public class NBCBUserInfoReqDto extends CommonReqDto {
    @JsonProperty("user_id")
    private String userId;

    @JsonProperty("order_no")
    private String orderNo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
