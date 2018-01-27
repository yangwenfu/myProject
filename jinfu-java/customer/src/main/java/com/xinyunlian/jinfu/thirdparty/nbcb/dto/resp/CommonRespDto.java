package com.xinyunlian.jinfu.thirdparty.nbcb.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bright on 2017/5/17.
 */
public class CommonRespDto {
    @JsonProperty("resp_code")
    private String respCode;

    @JsonProperty("resp_message")
    private String respMessage;

    @JsonProperty("sign")
    private String sign;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespMessage() {
        return respMessage;
    }

    public void setRespMessage(String respMessage) {
        this.respMessage = respMessage;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
