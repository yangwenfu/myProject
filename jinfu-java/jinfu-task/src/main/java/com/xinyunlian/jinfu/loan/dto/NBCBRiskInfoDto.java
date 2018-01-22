package com.xinyunlian.jinfu.loan.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by bright on 2017/5/26.
 */
public class NBCBRiskInfoDto implements Serializable {
    private static final long serialVersionUID = 7743970399830152645L;

    @JsonProperty("risk_users")
    private String riskUsers;

    private String timestamp;

    private String sign;

    public NBCBRiskInfoDto() {
    }

    public NBCBRiskInfoDto(String riskUsers, String timestamp, String sign) {
        this.riskUsers = riskUsers;
        this.timestamp = timestamp;
        this.sign = sign;
    }

    public String getRiskUsers() {
        return riskUsers;
    }

    public void setRiskUsers(String riskUsers) {
        this.riskUsers = riskUsers;
    }

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
