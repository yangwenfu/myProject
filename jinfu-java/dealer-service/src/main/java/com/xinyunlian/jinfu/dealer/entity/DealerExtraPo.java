package com.xinyunlian.jinfu.dealer.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by menglei on 2016年08月26日.
 */
@Entity
@Table(name = "dealer_extra")
public class DealerExtraPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "DEALER_ID")
    private String dealerId;

    @Column(name = "BIZ_LICENCE")
    private String bizLicence;

    @Column(name = "BIZ_LICENCE_PIC")
    private String bizLicencePic;

    @Column(name = "CONTACT")
    private String contact;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "ID_CARD_NO")
    private String idCardNo;

    @Column(name = "ID_CARD_PIC1")
    private String idCardNoPic1;

    @Column(name = "ID_CARD_PIC2")
    private String idCardNoPic2;

    @Column(name = "FINAN_CONTACT")
    private String finanContact;

    @Column(name = "FINAN_PHONE")
    private String finanPhone;

    @Column(name = "BANK_NAME")
    private String bankName;

    @Column(name = "BANK_CODE")
    private String bankCode;

    @Column(name = "BANK_CARD_NAME")
    private String bankCardName;

    @Column(name = "BANK_CARD_NO")
    private String bankCardNo;

    @Column(name = "ACCOUNT_LICENCE_PIC")
    private String accountLicencePic;

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getBizLicence() {
        return bizLicence;
    }

    public void setBizLicence(String bizLicence) {
        this.bizLicence = bizLicence;
    }

    public String getBizLicencePic() {
        return bizLicencePic;
    }

    public void setBizLicencePic(String bizLicencePic) {
        this.bizLicencePic = bizLicencePic;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getIdCardNoPic1() {
        return idCardNoPic1;
    }

    public void setIdCardNoPic1(String idCardNoPic1) {
        this.idCardNoPic1 = idCardNoPic1;
    }

    public String getIdCardNoPic2() {
        return idCardNoPic2;
    }

    public void setIdCardNoPic2(String idCardNoPic2) {
        this.idCardNoPic2 = idCardNoPic2;
    }

    public String getFinanContact() {
        return finanContact;
    }

    public void setFinanContact(String finanContact) {
        this.finanContact = finanContact;
    }

    public String getFinanPhone() {
        return finanPhone;
    }

    public void setFinanPhone(String finanPhone) {
        this.finanPhone = finanPhone;
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

    public String getBankCardName() {
        return bankCardName;
    }

    public void setBankCardName(String bankCardName) {
        this.bankCardName = bankCardName;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getAccountLicencePic() {
        return accountLicencePic;
    }

    public void setAccountLicencePic(String accountLicencePic) {
        this.accountLicencePic = accountLicencePic;
    }
}
