package com.xinyunlian.jinfu.carbank.api.dto.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author willwang
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CityResponse extends CarBankResponse {

    @JsonProperty("content")
    private List<CityCntResponse> content;

    public List<CityCntResponse> getContent() {
        return content;
    }

    public void setContent(List<CityCntResponse> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "CityResponse{" +
                "content=" + content +
                '}';
    }
}
