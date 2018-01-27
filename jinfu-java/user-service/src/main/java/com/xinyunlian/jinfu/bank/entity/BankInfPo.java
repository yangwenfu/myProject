package com.xinyunlian.jinfu.bank.entity;


import javax.persistence.*;

/**
 * 银行Entity
 * @author KimLL
 * @version
 */
@Entity
@Table(name = "sys_bank_inf")
public class BankInfPo {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BANK_ID")
    private Long bankId;

    @Column(name = "BANK_CNAPS_CODE")
    private String bankCnapsCode;

    @Column(name = "BANK_NAME")
    private String bankName;

    @Column(name = "BANK_CODE")
    private String	bankCode;

    @Column(name = "SUPPORT")
    private boolean support;

    @Column(name = "BANK_LOGO")
    private String bankLogo;

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public String getBankCnapsCode() {
        return bankCnapsCode;
    }

    public void setBankCnapsCode(String bankCnapsCode) {
        this.bankCnapsCode = bankCnapsCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public boolean isSupport() {
        return support;
    }

    public void setSupport(boolean support) {
        this.support = support;
    }

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo;
    }
}


