package com.xinyunlian.jinfu.domain.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.domain.SignatureIgnore;
import com.xinyunlian.jinfu.domain.resp.PayRecvResponse;
import com.xinyunlian.jinfu.enums.ETrxType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cong on 2016/5/29.
 */
public class PayRecvRequest extends CommonCommandRequest<PayRecvResponse> {

    @SignatureIgnore
    @JsonIgnore
    private static final Logger LOGGER = LoggerFactory.getLogger(PayRecvRequest.class);

    @JsonProperty("tran_type")
    private String tranType;

    @JsonProperty("tran_amt")
    private String tranAmt;

    @JsonProperty("tran_no")
    private String tranNo;

    @JsonProperty("login_id")
    private String loginId;

    @JsonProperty("acc_no")
    private String accNo;

    @JsonProperty("acc_name")
    private String accName;

    @JsonProperty("certify_type")
    private String certifyType;

    @JsonProperty("certify_no")
    private String certifyNo;

    @JsonProperty("org_code")
    private String orgCode;

    @JsonProperty("acc_type")
    private String accType;

    private String purpose;

    @SignatureIgnore
    @JsonIgnore
    private String executeUrl = AppConfigUtil.getConfig("pay.domain") + "/gateway-controller/tran/v2/proxytran.htm";

    @JsonIgnore
    @Override
    public ETrxType getTrxType() {
        return ETrxType.PAY_RECV;
    }

    @Override
    public PayRecvResponse execute() {
        try{
            return super.execute();
        } catch (Exception e) {
            LOGGER.error("execute payRecvRequest failed", e);
            return PayRecvResponse.ofError(e.getMessage());
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

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
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

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
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

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}
