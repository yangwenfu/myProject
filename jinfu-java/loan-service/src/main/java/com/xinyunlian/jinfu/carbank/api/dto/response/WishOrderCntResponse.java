package com.xinyunlian.jinfu.carbank.api.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/7/18/0018.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WishOrderCntResponse implements Serializable {
    private static final long serialVersionUID = -4190170569652608364L;

    @JsonProperty("loanApplyNo")
    private String loanApplyNo;

    public String getLoanApplyNo() {
        return loanApplyNo;
    }

    public void setLoanApplyNo(String loanApplyNo) {
        this.loanApplyNo = loanApplyNo;
    }
}
