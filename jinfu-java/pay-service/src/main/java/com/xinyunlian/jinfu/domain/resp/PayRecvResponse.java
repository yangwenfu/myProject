package com.xinyunlian.jinfu.domain.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xinyunlian.jinfu.dto.PayRecvResultDto;
import com.xinyunlian.jinfu.enums.ETrxType;
import com.xinyunlian.jinfu.enums.PayRecvResult;
import org.apache.commons.lang.StringUtils;

public class PayRecvResponse extends CommonCommandResponse {

    @JsonProperty("tran_amt")
    private String tranAmt;

    @JsonProperty("tran_no")
    private String tranNo;

    @JsonProperty("xyl_tran_no")
    private String xylTranNo;

    @JsonProperty("tran_date")
    private String tranDate;

    @JsonProperty("settle_date")
    private String settleDate;

    @JsonProperty("tran_status")
    private String tranStatus;

    @Override
    public ETrxType getTrxType() {
        return ETrxType.PAY_RECV;
    }

    public static PayRecvResponse ofNull() {
        return new PayRecvResponse();
    }

    public static PayRecvResponse ofError(String message) {
        PayRecvResponse response = new PayRecvResponse();
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

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getTranStatus() {
        return tranStatus;
    }

    public void setTranStatus(String tranStatus) {
        this.tranStatus = tranStatus;
    }

    @Override
    public String toString() {
        return "PayRecvResponse{" +
                "tranAmt='" + tranAmt + '\'' +
                ", tranNo='" + tranNo + '\'' +
                ", xylTranNo='" + xylTranNo + '\'' +
                ", tranDate='" + tranDate + '\'' +
                ", settleDate='" + settleDate + '\'' +
                "} " + super.toString();
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
            return PayRecvResult.FAILED.setRetInfo(getRetCode(), getRetMsg());
        }
    }
}
