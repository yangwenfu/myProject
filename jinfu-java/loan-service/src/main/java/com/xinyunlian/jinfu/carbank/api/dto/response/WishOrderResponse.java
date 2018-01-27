package com.xinyunlian.jinfu.carbank.api.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by dongfangchao on 2017/7/18/0018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WishOrderResponse extends CarBankResponse {
    private static final long serialVersionUID = 1956207589357472409L;

    @JsonProperty("content")
    private WishOrderCntResponse content;

    public WishOrderCntResponse getContent() {
        return content;
    }

    public void setContent(WishOrderCntResponse content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "WishOrderResponse{" +
                "content=" + content +
                '}';
    }
}
