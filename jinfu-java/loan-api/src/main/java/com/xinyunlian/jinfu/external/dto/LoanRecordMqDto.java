package com.xinyunlian.jinfu.external.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by godslhand on 2017/8/1.
 */
public class LoanRecordMqDto implements Serializable
{
    private String applyId ;
    private BigDecimal amount;// 审核失败的时候是0
    private String fundType;

    public LoanRecordMqDto() {
    }

    public LoanRecordMqDto(String applyId, BigDecimal amount, String fundType) {
        this.applyId = applyId;
        this.amount = amount;
        this.fundType = fundType;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }
}
