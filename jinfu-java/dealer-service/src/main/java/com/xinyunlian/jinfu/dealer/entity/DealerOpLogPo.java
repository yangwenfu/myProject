package com.xinyunlian.jinfu.dealer.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.dealer.enums.EDealerOpLogType;
import com.xinyunlian.jinfu.dealer.enums.converter.EDealerOpLogTypeConverter;

import javax.persistence.*;

/**
 * Created by menglei on 2017年05月09日.
 */
@Entity
@Table(name = "dealer_op_log")
public class DealerOpLogPo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "OPERATOR")
    private String opeartor;

    @Column(name = "OP_LOG")
    private String opLog;

    @Column(name = "DEALER_ID")
    private String dealerId;

    @Column(name = "TYPE")
    @Convert(converter = EDealerOpLogTypeConverter.class)
    private EDealerOpLogType type;

    @Column(name = "FRONT_BASE")
    private String frontBase;

    @Column(name = "AFTER_BASE")
    private String afterBase;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpeartor() {
        return opeartor;
    }

    public void setOpeartor(String opeartor) {
        this.opeartor = opeartor;
    }

    public String getOpLog() {
        return opLog;
    }

    public void setOpLog(String opLog) {
        this.opLog = opLog;
    }

    public String getFrontBase() {
        return frontBase;
    }

    public void setFrontBase(String frontBase) {
        this.frontBase = frontBase;
    }

    public String getAfterBase() {
        return afterBase;
    }

    public void setAfterBase(String afterBase) {
        this.afterBase = afterBase;
    }

    public EDealerOpLogType getType() {
        return type;
    }

    public void setType(EDealerOpLogType type) {
        this.type = type;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }
}
