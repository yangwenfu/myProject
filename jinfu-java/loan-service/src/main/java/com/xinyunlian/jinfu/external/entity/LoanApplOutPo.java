package com.xinyunlian.jinfu.external.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.loan.enums.EApplOutType;
import com.xinyunlian.jinfu.external.enums.converter.EApplOutTypeEnumConverter;

import javax.persistence.*;

/**
 * @author willwang
 */
@Entity
@Table(name = "FP_LOAN_APPL_OUT")
public class LoanApplOutPo extends BaseMaintainablePo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private String id;

    @Column(name = "APPL_ID")
    private String applId;

    @Column(name = "OUT_TRADE_NO")
    private String outTradeNo;

    @Column(name = "stype")
    @Convert(converter = EApplOutTypeEnumConverter.class)
    private EApplOutType type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
