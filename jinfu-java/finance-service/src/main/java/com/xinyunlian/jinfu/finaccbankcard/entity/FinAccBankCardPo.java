package com.xinyunlian.jinfu.finaccbankcard.entity;

import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;
import com.xinyunlian.jinfu.finaccbankcard.enums.EFinOrg;
import com.xinyunlian.jinfu.finaccbankcard.enums.converter.EFinOrgConverter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dongfangchao on 2016/11/21.
 */
@Entity
@Table(name = "fin_acc_bank_card")
@EntityListeners(IdInjectionEntityListener.class)
public class FinAccBankCardPo implements Serializable{
    private static final long serialVersionUID = -89804992460322169L;

    @Id
    @Column(name = "ID")
    private String Id;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "EXT_TX_ACC_ID")
    private String extTxAccId;

    @Column(name = "USER_REAL_NAME")
    private String userRealName;

    @Column(name = "ID_CARD_NO")
    private String idCardNo;

    @Column(name = "BANK_CARD_NO")
    private String bankCardNo;

    @Column(name = "RESERVE_MOBILE")
    private String reserveMobile;

    @Column(name = "BANK_SHORT_NAME")
    private String bankShortName;

    @Column(name = "FIN_ORG")
    @Convert(converter = EFinOrgConverter.class)
    private EFinOrg finOrg;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExtTxAccId() {
        return extTxAccId;
    }

    public void setExtTxAccId(String extTxAccId) {
        this.extTxAccId = extTxAccId;
    }

    public String getUserRealName() {
        return userRealName;
    }

    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getReserveMobile() {
        return reserveMobile;
    }

    public void setReserveMobile(String reserveMobile) {
        this.reserveMobile = reserveMobile;
    }

    public EFinOrg getFinOrg() {
        return finOrg;
    }

    public void setFinOrg(EFinOrg finOrg) {
        this.finOrg = finOrg;
    }

    public String getBankShortName() {
        return bankShortName;
    }

    public void setBankShortName(String bankShortName) {
        this.bankShortName = bankShortName;
    }
}
