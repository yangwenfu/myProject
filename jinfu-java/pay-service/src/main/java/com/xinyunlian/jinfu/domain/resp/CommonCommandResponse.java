package com.xinyunlian.jinfu.domain.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xinyunlian.jinfu.domain.CommandResponse;
import com.xinyunlian.jinfu.enums.ETrxType;

/**
 * Created by cong on 2016/5/29.
 */
public abstract class CommonCommandResponse extends CommandResponse {

    @JsonProperty("result_code")
    private String retCode;

    @JsonProperty("result_msg")
    private String retMsg;

    private String charset;

    @JsonProperty("sign_type")
    private String signType;

    @JsonProperty("sign_msg")
    private String signMsg;



    public static CommonCommandResponse ofNull(ETrxType TrxType) {
        return new CommonCommandResponse() {
            @Override
            public ETrxType getTrxType() {
                return TrxType;
            }

            @Override
            public String getRetCode() {
                return "-1";
            }
        };
    }


    @Override
    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    @Override
    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSignMsg() {
        return signMsg;
    }

    public void setSignMsg(String signMsg) {
        this.signMsg = signMsg;
    }

    @Override
    public String toString() {
        return "CommonCommandResponse{" +
                "retCode='" + retCode + '\'' +
                ", retMsg='" + retMsg + '\'' +
                ", charset='" + charset + '\'' +
                ", signType='" + signType + '\'' +
                ", signMsg='" + signMsg + '\'' +
                '}';
    }

}
