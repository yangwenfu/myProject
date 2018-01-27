package com.xinyunlian.jinfu.finance.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/1/3/0003.
 */
public class SuperCashTradeQueryDto implements Serializable {
    private static final long serialVersionUID = 8254041167115365714L;

    @NotEmpty(message = "理财账号不能为空")
    private String transactionAccountID;

    @NotEmpty(message = "申请单号不能为空")
    private String applicationNo;

    @NotNull(message = "基金ID不能为空")
    private Long finFundId;

    public Long getFinFundId() {
        return finFundId;
    }

    public void setFinFundId(Long finFundId) {
        this.finFundId = finFundId;
    }

    public String getTransactionAccountID() {
        return transactionAccountID;
    }

    public void setTransactionAccountID(String transactionAccountID) {
        this.transactionAccountID = transactionAccountID;
    }

    public String getApplicationNo() {
        return applicationNo;
    }

    public void setApplicationNo(String applicationNo) {
        this.applicationNo = applicationNo;
    }
}
