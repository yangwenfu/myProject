package com.xinyunlian.jinfu.domain.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xinyunlian.jinfu.enums.ETrxType;
import com.xinyunlian.jinfu.enums.PayRecvResult;
import org.apache.commons.lang.StringUtils;

/**
 * Created by cong on 2016/5/29.
 */
public class RealAuthResponse extends CommonCommandResponse {

    @JsonProperty("acc_no")
    private String acctNo;

    @JsonProperty("certify_type")
    private String certifyType = "01";

    @JsonProperty("certify_no")
    private String certifyNo;

    @JsonProperty("cust_name")
    private String custName;

    @JsonProperty("mobile_no")
    private String mobileNo;

    @Override
    public ETrxType getTrxType() {
        return ETrxType.RAEL_AUTH;
    }

    public static RealAuthResponse ofNull() {
        return new RealAuthResponse();
    }

    public static RealAuthResponse ofError(String message) {
        RealAuthResponse response = new RealAuthResponse();
        response.setRetMsg(message);
        return response;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getCertifyType() {
        return certifyType;
    }

    public void setCertifyType(String certifyType) {
        this.certifyType = certifyType;
    }

    public String getCertifyNo() {
        return certifyNo;
    }

    public void setCertifyNo(String certifyNo) {
        this.certifyNo = certifyNo;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    @Override
    public String toString() {
        return "AcctAuthResponse{" +
                "acctNo='" + acctNo + '\'' +
                ", certifyType='" + certifyType + '\'' +
                ", certifyNo='" + certifyNo + '\'' +
                ", custName='" + custName + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                "} " + super.toString();
    }

    public PayRecvResult getResult() {
        if (StringUtils.equals(getRetCode(), "0000")) {
            return PayRecvResult.SUCCESS.setRetInfo(getRetCode(), getRetMsg());
        } else {
            return PayRecvResult.FAILED.setRetInfo(getRetCode(), getRetMsg());
        }
    }
}
