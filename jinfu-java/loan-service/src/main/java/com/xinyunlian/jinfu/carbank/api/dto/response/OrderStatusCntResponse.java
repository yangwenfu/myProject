package com.xinyunlian.jinfu.carbank.api.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/7/18/0018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderStatusCntResponse implements Serializable {
    private static final long serialVersionUID = -7980225764013401365L;

    @JsonProperty("changeOrderDate")
    private String changeOrderDate;

    @JsonProperty("issueLoanDate")
    private String issueLoanDate;

    @JsonProperty("loanAmt")
    private String loanAmt;

    @JsonProperty("reason")
    private String reason;

    @JsonProperty("status")
    private Integer status;

    @JsonProperty("term")
    private String term;

    public String getIssueLoanDate() {
        return issueLoanDate;
    }

    public void setIssueLoanDate(String issueLoanDate) {
        this.issueLoanDate = issueLoanDate;
    }

    public String getLoanAmt() {
        return loanAmt;
    }

    public void setLoanAmt(String loanAmt) {
        this.loanAmt = loanAmt;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getChangeOrderDate() {
        return changeOrderDate;
    }

    public void setChangeOrderDate(String changeOrderDate) {
        this.changeOrderDate = changeOrderDate;
    }
}
