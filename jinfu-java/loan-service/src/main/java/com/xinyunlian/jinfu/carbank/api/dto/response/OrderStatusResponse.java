package com.xinyunlian.jinfu.carbank.api.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by dongfangchao on 2017/7/18/0018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderStatusResponse extends CarBankResponse {
    private static final long serialVersionUID = -4481223192390907907L;

    @JsonProperty("content")
    private OrderStatusCntResponse content;

    public OrderStatusCntResponse getContent() {
        return content;
    }

    public void setContent(OrderStatusCntResponse content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "OrderStatusResponse{" +
                "content=" + content +
                '}';
    }
}
