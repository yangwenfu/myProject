package com.xinyunlian.jinfu.loan.dto.btest;

import com.xinyunlian.jinfu.loan.dto.resp.apply.LoanBankDto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author willwang
 */
public class BTestApplDto implements Serializable{

    /**
     * 可借款金额
     */
    private BigDecimal amt;

    /**
     * 银行卡信息
     */
    private LoanBankDto bank;

    /**
     * 推荐人手机号
     */
    private String recUser;

    /**
     * 推荐人姓名
     */
    private String recName;

    public String getRecUser() {
        return recUser;
    }

    public void setRecUser(String recUser) {
        this.recUser = recUser;
    }

    public String getRecName() {
        return recName;
    }

    public void setRecName(String recName) {
        this.recName = recName;
    }

    /**
     * 还款方式
     */
    private List<BTestRepayMode> repayModes;

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public LoanBankDto getBank() {
        return bank;
    }

    public void setBank(LoanBankDto bank) {
        this.bank = bank;
    }

    public List<BTestRepayMode> getRepayModes() {
        return repayModes;
    }

    public void setRepayModes(List<BTestRepayMode> repayModes) {
        this.repayModes = repayModes;
    }
}

