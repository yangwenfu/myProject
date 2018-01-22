package com.xinyunlian.jinfu.loan.dto.depository.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author willwang
 */
public class LoanDepositoryRepayConfirmDto implements Serializable {

    private String bankCardNo;

    private BigDecimal amt;

    private String mobile;

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
