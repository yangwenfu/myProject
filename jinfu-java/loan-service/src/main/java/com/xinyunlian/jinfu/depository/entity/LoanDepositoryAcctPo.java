package com.xinyunlian.jinfu.depository.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.common.entity.BasePo;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author willwang
 */
@Entity
@Table(name = "fp_loan_depository_acct")
public class LoanDepositoryAcctPo extends BaseMaintainablePo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ACCT_NO")
    private String acctNo;

    @Column(name = "BANK_CARD_ID")
    private Long bankCardId;

    @Column(name = "BANK_CARD_NO")
    private String bankCardNo;

    @Column(name = "ID_CARD_NO")
    private String idCardNo;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MOBILE")
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public Long getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(Long bankCardId) {
        this.bankCardId = bankCardId;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
