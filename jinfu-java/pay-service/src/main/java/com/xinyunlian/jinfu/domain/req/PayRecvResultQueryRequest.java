package com.xinyunlian.jinfu.domain.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.domain.SignatureIgnore;
import com.xinyunlian.jinfu.domain.resp.PayRecvResultQueryResponse;
import com.xinyunlian.jinfu.enums.ETrxType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cong on 2016/5/29.
 */
public class PayRecvResultQueryRequest extends CommonCommandRequest<PayRecvResultQueryResponse> {

    @SignatureIgnore
    private static final Logger LOGGER = LoggerFactory.getLogger(PayRecvRequest.class);

    @JsonProperty("tran_no")
    private String tranNo;

    @JsonProperty("xyl_tran_no")
    private String xylTranNo;

    @JsonProperty("tran_type")
    private String tranType;

    @JsonProperty("tran_date")
    private String tranDate;

    @SignatureIgnore
    @JsonIgnore
    private String executeUrl = AppConfigUtil.getConfig("pay.domain") + "/gateway-controller/tran/proxytranquery.htm";

    @JsonIgnore
    @Override
    public ETrxType getTrxType() {
        return ETrxType.PAY_RECV_RESULT_QUERY;
    }


    @Override
    public PayRecvResultQueryResponse execute() {
        try{
            return super.execute();
        } catch (Exception e) {
            LOGGER.error("execute payRecvRequest failed", e);
            return PayRecvResultQueryResponse.ofError(e.getMessage());
        }
    }

    @Override
    public String getExecuteUrl() {
        return executeUrl;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getTranNo() {
        return tranNo;
    }

    public void setTranNo(String tranNo) {
        this.tranNo = tranNo;
    }

    public String getXylTranNo() {
        return xylTranNo;
    }

    public void setXylTranNo(String xylTranNo) {
        this.xylTranNo = xylTranNo;
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public void setExecuteUrl(String executeUrl) {
        this.executeUrl = executeUrl;
    }
}
