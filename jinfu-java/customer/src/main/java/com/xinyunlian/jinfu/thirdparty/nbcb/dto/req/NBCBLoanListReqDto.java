package com.xinyunlian.jinfu.thirdparty.nbcb.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bright on 2017/5/18.
 */
public class NBCBLoanListReqDto extends CommonReqDto {
    @JsonProperty("loans")
    private String loans;

    public String getLoans() {
        return loans;
    }

    public void setLoans(String loans) {
        this.loans = loans;
    }
}
