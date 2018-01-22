package com.xinyunlian.jinfu.loan.dto.req;

import com.xinyunlian.jinfu.loan.dto.resp.LoanDtlDto;
import com.xinyunlian.jinfu.pay.enums.EPrType;

import java.io.Serializable;

/**
 * Created by Willwang on 2016/12/9.
 */
public class LoanPayReq implements Serializable {

    //贷款编号
    private String loanId;

    //银行名称
    private String bankCardName;

    //银行卡号
    private String bankCardNo;

    //身份证号
    private String idCardNo;

    //银行编码
    private String bankCode;

    /**
     * 用户手机号
     */
    private String userMobile;

    /**
     * 银行预留手机号
     */
    private String bankCardMobile;

    /**
     * 交易类型,标注了是否存在真实交易的行为
     */
    private EPrType prType;

    /**
     * 贷款实体
     * @return
     */
    private LoanDtlDto loanDtlDto;

    public LoanDtlDto getLoanDtlDto() {
        return loanDtlDto;
    }

    public void setLoanDtlDto(LoanDtlDto loanDtlDto) {
        this.loanDtlDto = loanDtlDto;
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

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getBankCardMobile() {
        return bankCardMobile;
    }

    public void setBankCardMobile(String bankCardMobile) {
        this.bankCardMobile = bankCardMobile;
    }

    public EPrType getPrType() {
        return prType;
    }

    public void setPrType(EPrType prType) {
        this.prType = prType;
    }

    @Override
    public String toString() {
        return "LoanPayReq{" +
                "loanId='" + loanId + '\'' +
                ", bankCardName='" + bankCardName + '\'' +
                ", bankCardNo='" + bankCardNo + '\'' +
                ", idCardNo='" + idCardNo + '\'' +
                ", bankCode='" + bankCode + '\'' +
                ", userMobile='" + userMobile + '\'' +
                ", bankCardMobile='" + bankCardMobile + '\'' +
                ", prType=" + prType +
                ", loanDtlDto=" + loanDtlDto +
                '}';
    }
}
