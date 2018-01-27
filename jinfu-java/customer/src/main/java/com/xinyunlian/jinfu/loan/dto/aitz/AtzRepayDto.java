package com.xinyunlian.jinfu.loan.dto.aitz;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author willwang
 */
public class AtzRepayDto implements Serializable{

    /**
     * 还款总额
     */
    private BigDecimal amt;

    /**
     * 应还本金
     */
    private BigDecimal capital;

    /**
     * 应还利息
     */
    private BigDecimal interest;

    /**
     * 借款人姓名
     */
    private String name;

    /**
     * 身份证号
     */
    private String idCardNo;

    /**
     * 收/还款账户
     */
    private String bankCardNo;

    /**
     * 描述备注信息
     */
    private String desc;

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public BigDecimal getCapital() {
        return capital;
    }

    public void setCapital(BigDecimal capital) {
        this.capital = capital;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
