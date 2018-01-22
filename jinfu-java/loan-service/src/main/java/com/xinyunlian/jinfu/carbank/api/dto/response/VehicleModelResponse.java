package com.xinyunlian.jinfu.carbank.api.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by dongfangchao on 2017/7/18/0018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehicleModelResponse extends CarBankResponse {

    private static final long serialVersionUID = -4557186513331425969L;

    @JsonProperty("content")
    private List<VehicleModelCntResponse> content;

    public List<VehicleModelCntResponse> getContent() {
        return content;
    }

    public void setContent(List<VehicleModelCntResponse> content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "VehicleModelResponse{" +
                "content=" + content +
                '}';
    }
}
