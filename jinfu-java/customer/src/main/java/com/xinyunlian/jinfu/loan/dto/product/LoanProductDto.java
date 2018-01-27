package com.xinyunlian.jinfu.loan.dto.product;

import com.xinyunlian.jinfu.loan.enums.ERepayMode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Willwang on 2017/1/5.
 */
public class LoanProductDto implements Serializable {

    /**
     * 产品编号
     */
    private String id;

    /**
     * 产品名称
     */
    private String name;

    /**
     * 还款方式
     */
    private ERepayMode repayMode;

    /**
     * 最大贷款额度
     */
    private BigDecimal loanAmtMax;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ERepayMode getRepayMode() {
        return repayMode;
    }

    public void setRepayMode(ERepayMode repayMode) {
        this.repayMode = repayMode;
    }

    public BigDecimal getLoanAmtMax() {
        return loanAmtMax;
    }

    public void setLoanAmtMax(BigDecimal loanAmtMax) {
        this.loanAmtMax = loanAmtMax;
    }
}
