package com.xinyunlian.jinfu.thirdparty.nbcb.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bright on 2017/5/17.
 */
public class CommonReqDto {
    @JsonProperty("timestamp")
    private String timestamp;

    @JsonProperty("sign")
    private String sign;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
