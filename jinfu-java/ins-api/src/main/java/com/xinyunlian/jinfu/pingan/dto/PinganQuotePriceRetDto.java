package com.xinyunlian.jinfu.pingan.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
public class PinganQuotePriceRetDto implements Serializable {
    private static final long serialVersionUID = 135117150966197267L;

    //保额
    private BigDecimal insuredAmount;

    //保费
    private BigDecimal insuredPremium;

    private String productCode;

    private List<String> planCodeList;

    public BigDecimal getInsuredAmount() {
        return insuredAmount;
    }

    public void setInsuredAmount(BigDecimal insuredAmount) {
        this.insuredAmount = insuredAmount;
    }

    public BigDecimal getInsuredPremium() {
        return insuredPremium;
    }

    public void setInsuredPremium(BigDecimal insuredPremium) {
        this.insuredPremium = insuredPremium;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public List<String> getPlanCodeList() {
        return planCodeList;
    }

    public void setPlanCodeList(List<String> planCodeList) {
        this.planCodeList = planCodeList;
    }
}
