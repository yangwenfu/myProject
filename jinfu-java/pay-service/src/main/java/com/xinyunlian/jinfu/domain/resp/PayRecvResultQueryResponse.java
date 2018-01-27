package com.xinyunlian.jinfu.domain.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xinyunlian.jinfu.dto.PayRecvResultDto;
import com.xinyunlian.jinfu.enums.ETrxType;
import com.xinyunlian.jinfu.enums.PayRecvResult;
import org.apache.commons.lang.StringUtils;

/**
 * Created by cong on 2016/5/29.
 */
public class PayRecvResultQueryResponse extends CommonCommandResponse {

    @JsonProperty("tran_amt")
    private String tranAmt;

    @JsonProperty("tran_no")
    private String tranNo;

    @JsonProperty("xyl_tran_no")
    private String xylTranNo;

    @JsonProperty("tran_type")
    private String tranType;

    @JsonProperty("tran_msg")
    private String tranMsg;

    @JsonProperty("success_time")
    private String successTime;

    @JsonProperty("acc_no")
    private String accNo;

    @JsonProperty("acc_name")
    private String accName;

    @JsonProperty("acc_type")
    private String accType;

    @JsonProperty("tran_status")
    private String tranStatus;

    @Override
    public ETrxType getTrxType() {
        return ETrxType.PAY_RECV_RESULT_QUERY;
    }


    public static PayRecvResultQueryResponse ofError(String message) {
        PayRecvResultQueryResponse response = new PayRecvResultQueryResponse();
        response.setRetMsg(message);
        return response;
    }

    public String getTranAmt() {
        return tranAmt;
    }

    public void setTranAmt(String tranAmt) {
        this.tranAmt = tranAmt;
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

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getTranMsg() {
        return tranMsg;
    }

    public void setTranMsg(String tranMsg) {
        this.tranMsg = tranMsg;
    }

    public String getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(String successTime) {
        this.successTime = successTime;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getTranStatus() {
        return tranStatus;
    }

    public void setTranStatus(String tranStatus) {
        this.tranStatus = tranStatus;
    }

    public PayRecvResult getResult() {
        if (StringUtils.equals(getRetCode(), "0000")) {
            if (StringUtils.equals(tranStatus, "00")) {
                return PayRecvResult.SUCCESS.setRetInfo(getRetCode(), getRetMsg());
            } else if (StringUtils.equals(tranStatus, "01")) {
                return PayRecvResult.FAILED.setRetInfo(getRetCode(), getRetMsg());
            } else {
                return PayRecvResult.PROCESS.setRetInfo(getRetCode(), getRetMsg());
            }
        } else {
            return PayRecvResult.PROCESS.setRetInfo(getRetCode(), getRetMsg());
        }
    }

}
