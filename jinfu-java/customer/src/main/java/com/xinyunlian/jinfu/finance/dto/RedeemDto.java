package com.xinyunlian.jinfu.finance.dto;

import com.xinyunlian.jinfu.fintxhistory.enums.ERedeemType;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by dongfangchao on 2017/1/3/0003.
 */
public class RedeemDto implements Serializable {
    private static final long serialVersionUID = -8176749858960735530L;

    @NotNull(message = "赎回金额不能为空")
    private BigDecimal redeemAmt;
    @NotEmpty(message = "银行卡号码不能为空")
    private String bankCardNo;
    @NotNull(message = "基金id不能为空")
    private Long fundId;
    @NotNull(message = "赎回类型不能为空")
    private ERedeemType redeemType;

    public BigDecimal getRedeemAmt() {
        return redeemAmt;
    }

    public void setRedeemAmt(BigDecimal redeemAmt) {
        this.redeemAmt = redeemAmt;
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

    public ERedeemType getRedeemType() {
        return redeemType;
    }

    public void setRedeemType(ERedeemType redeemType) {
        this.redeemType = redeemType;
    }
}
