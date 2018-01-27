package com.xinyunlian.jinfu.user.dto;

import com.xinyunlian.jinfu.picture.dto.PictureDto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 银行流水账户Entity
 *
 * @author jll
 */

public class UserBankAcctDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long bankAccountId;

    private String userId;
    //银行卡号
    private String bankCardNo;
    //银行名称
    private String bankName;
    //账户余额
    private BigDecimal balance;
    //流水开始日期
    private Date tradeBegin;
    //流水结束日期
    private Date tradeEnd;

    private List<PictureDto> pictureDtos = new ArrayList<>();

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getTradeBegin() {
        return tradeBegin;
    }

    public void setTradeBegin(Date tradeBegin) {
        this.tradeBegin = tradeBegin;
    }

    public Date getTradeEnd() {
        return tradeEnd;
    }

    public void setTradeEnd(Date tradeEnd) {
        this.tradeEnd = tradeEnd;
    }

    public List<PictureDto> getPictureDtos() {
        return pictureDtos;
    }

    public void setPictureDtos(List<PictureDto> pictureDtos) {
        this.pictureDtos = pictureDtos;
    }

    @Override
    public String toString() {
        return "UserBankAcctDto{" +
                "bankAccountId=" + bankAccountId +
                ", userId='" + userId + '\'' +
                ", bankCardNo='" + bankCardNo + '\'' +
                ", bankName='" + bankName + '\'' +
                ", balance=" + balance +
                ", tradeBegin=" + tradeBegin +
                ", tradeEnd=" + tradeEnd +
                ", pictureDtos=" + pictureDtos +
                '}';
    }
}


