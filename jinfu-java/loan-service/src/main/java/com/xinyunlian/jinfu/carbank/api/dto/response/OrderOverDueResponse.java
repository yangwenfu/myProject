package com.xinyunlian.jinfu.carbank.api.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by dongfangchao on 2017/7/18/0018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderOverDueResponse extends CarBankResponse {
    private static final long serialVersionUID = -2007920827486347911L;

    @JsonProperty("content")
    private OrderOverDueCntResponse content;

    public OrderOverDueCntResponse getContent() {
        return content;
    }

    public void setContent(OrderOverDueCntResponse content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "OrderOverDueResponse{" +
                "content=" + content +
                '}';
    }
}
