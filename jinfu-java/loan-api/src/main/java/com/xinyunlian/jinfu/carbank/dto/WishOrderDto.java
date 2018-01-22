package com.xinyunlian.jinfu.carbank.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/7/18/0018.
 */
public class WishOrderDto implements Serializable {
    private static final long serialVersionUID = -4190170569652608364L;

    private String loanApplyNo;

    public String getLoanApplyNo() {
        return loanApplyNo;
    }

    public void setLoanApplyNo(String loanApplyNo) {
        this.loanApplyNo = loanApplyNo;
    }
}
