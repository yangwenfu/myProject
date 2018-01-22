package com.xinyunlian.jinfu.external.dto;

import com.xinyunlian.jinfu.loan.enums.EApplOutType;
import java.io.Serializable;

/**
 * @author willwang
 */
public class LoanApplOutDto implements Serializable{

    private String applId;

    private String outTradeNo;

    private EApplOutType type;

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public EApplOutType getType() {
        return type;
    }

    public void setType(EApplOutType type) {
        this.type = type;
    }
}
