package com.xinyunlian.jinfu.carbank.api.dto.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author willwang
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SeriesResponse extends CarBankResponse {

    @JsonProperty("content")
    private List<SeriesCntResponse> content;

    public List<SeriesCntResponse> getContent() {
        return content;
    }

    public void setContent(List<SeriesCntResponse> content) {
        this.content = content;
    }
}
