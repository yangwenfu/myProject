package com.xinyunlian.jinfu.carbank.api.dto.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author willwang
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BrandResponse extends CarBankResponse {

    @JsonProperty("content")
    private List<BrandCntResponse> content;

    public List<BrandCntResponse> getContent() {
        return content;
    }

    public void setContent(List<BrandCntResponse> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "BrandResponse{" +
                "content=" + content +
                '}';
    }
}
