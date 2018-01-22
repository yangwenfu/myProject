package com.xinyunlian.jinfu.domain.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xinyunlian.jinfu.common.util.AppConfigUtil;
import com.xinyunlian.jinfu.domain.SignatureIgnore;
import com.xinyunlian.jinfu.domain.resp.RealAuthResponse;
import com.xinyunlian.jinfu.enums.ETrxType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by cong on 2016/5/29.
 */
public class RealAuthRequest extends CommonCommandRequest<RealAuthResponse> {

    @SignatureIgnore
    private static final Logger LOGGER = LoggerFactory.getLogger(RealAuthRequest.class);

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

    @SignatureIgnore
    @JsonIgnore
    private String executeUrl = AppConfigUtil.getConfig("pay.domain") + "/gateway-controller/account/realauth.htm";

    @JsonIgnore
    @Override
    public ETrxType getTrxType() {
        return ETrxType.RAEL_AUTH;
    }

    @Override
    public RealAuthResponse execute() {
        try {
            return super.execute();
        } catch (Exception e) {
            LOGGER.error("execute real_auth failed", e);
            return RealAuthResponse.ofError(e.getMessage());
        }
    }

    @Override
    public String getExecuteUrl() {
        return executeUrl;
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

    public void setExecuteUrl(String executeUrl) {
        this.executeUrl = executeUrl;
    }
}
