package com.xinyunlian.jinfu.external.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.external.enums.converter.EApplOutTypeEnumConverter;
import com.xinyunlian.jinfu.loan.enums.EApplOutType;

import javax.persistence.*;

/**
 * @author willwang
 */
@Entity
@Table(name = "FP_LOAN_APPL_OUT_BANKCARD")
public class LoanApplOutBankCardPo extends BaseMaintainablePo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private String id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "APPL_ID")
    private String applId;

    @Column(name = "BANK_CARD_NO")
    private String bankCardNo;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }
}
