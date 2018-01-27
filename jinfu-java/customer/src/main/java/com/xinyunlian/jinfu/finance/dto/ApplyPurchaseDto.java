package com.xinyunlian.jinfu.finance.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by dongfangchao on 2017/1/3/0003.
 */
public class ApplyPurchaseDto implements Serializable {

    private static final long serialVersionUID = -2774928099341418749L;

    @NotNull(message = "申购金额不能为空")
    private BigDecimal purchase;
    @NotEmpty(message = "银行卡号不能为空")
    private String bankCardNo;
    @NotNull(message = "基金id不能为空")
    private Long fundId;

    public BigDecimal getPurchase() {
        return purchase;
    }

    public void setPurchase(BigDecimal purchase) {
        this.purchase = purchase;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public Long getFundId() {
        return fundId;
    }

    public void setFundId(Long fundId) {
        this.fundId = fundId;
    }
}
