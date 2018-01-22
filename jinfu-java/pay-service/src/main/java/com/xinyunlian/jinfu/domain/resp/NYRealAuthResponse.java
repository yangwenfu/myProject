package com.xinyunlian.jinfu.domain.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xinyunlian.jinfu.domain.CommandResponse;
import com.xinyunlian.jinfu.enums.ETrxType;
import com.xinyunlian.jinfu.enums.PayRecvResult;
import org.apache.commons.lang.StringUtils;

/**
 * Created by dell on 2016/11/2.
 */
public class NYRealAuthResponse extends CommandResponse {

    @JsonProperty("result")
    private String resultCode;

    @JsonProperty("sign")
    private String sign;

    @JsonProperty("respMsg")
    private String respMsg;

    @JsonProperty("respCode")
    private String respCode;

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getRespMsg() {
        return respMsg;
    }

    public void setRespMsg(String respMsg) {
        this.respMsg = respMsg;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    @Override
    public String toString() {
        return "AcctAuthResponse{" +
                "result='" + resultCode + '\'' +
                ", sign='" + sign + '\'' +
                ", respMsg='" + respMsg + '\'' +
                ", respCode='" + respCode + '\'' +
                "} " + super.toString();
    }

    public PayRecvResult getResult(){
        {
            if (StringUtils.equals(getRespCode(), "0000")) {
                return PayRecvResult.SUCCESS.setRetInfo(getRespCode(), getRespMsg());
            } else {
                return PayRecvResult.FAILED.setRetInfo(getRespCode(), getRespMsg());
            }
        }
    }

    public static NYRealAuthResponse ofError(String retMsg){
        NYRealAuthResponse response = new NYRealAuthResponse();
        response.setRespMsg(retMsg);
        return response;
    }

    public static NYRealAuthResponse ofNull(){
        return new NYRealAuthResponse();
    }

    @Override
    public ETrxType getTrxType() {
        return ETrxType.REAL_AUTH_NY;
    }

    @Override
    public String getRetMsg() {
        return this.getRespMsg();
    }

    @Override
    public String getRetCode() {
        return this.getRespCode();
    }
}
