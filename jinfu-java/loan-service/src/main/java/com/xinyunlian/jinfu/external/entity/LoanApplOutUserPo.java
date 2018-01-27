package com.xinyunlian.jinfu.external.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.external.enums.converter.EApplOutTypeEnumConverter;
import com.xinyunlian.jinfu.loan.enums.EApplOutType;

import javax.persistence.*;

/**
 * @author willwang
 */
@Entity
@Table(name = "FP_LOAN_APPL_OUT_USER")
public class LoanApplOutUserPo extends BaseMaintainablePo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private String id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "OUT_USER_ID")
    private String outUserId;

    @Column(name = "stype")
    @Convert(converter = EApplOutTypeEnumConverter.class)
    private EApplOutType type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOutUserId() {
        return outUserId;
    }

    public void setOutUserId(String outUserId) {
        this.outUserId = outUserId;
    }

    public EApplOutType getType() {
        return type;
    }

    public void setType(EApplOutType type) {
        this.type = type;
    }
}
