package com.xinyunlian.jinfu.api.dto;

import com.xinyunlian.jinfu.api.validate.dto.OpenApiBaseDto;
import com.xinyunlian.jinfu.bank.enums.ECardType;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by KimLL on 2016/8/18.
 */

public class BankCardOpenApi extends OpenApiBaseDto {
    private static final long serialVersionUID = 1L;
    private long bankCardId;
    @Length(max=50)
    @NotBlank(message = "金服用户ID不能为空")
    private String userId;
    //银行卡号
    @Length(max=40)
    @NotBlank(message = "银行卡号不能为空")
    private String bankCardNo;
    //持卡人姓名
    @Length(max=30)
    @NotBlank(message = "持卡人姓名不能为空")
    private String bankCardName;
    //预留手机号
    @Length(min=0, max=11)
    @NotBlank(message = "预留手机号不能为空")
    private String mobileNo;
    //身份证ID
    @Length(max=18)
    @NotBlank(message = "身份证号不能为空")
    private String idCardNo;
    //发卡行名称
    private String bankName;
    //银行简写
    private String bankCode;
    //支行名称
    private String subbranchName;
    //支行号
    private String subbranchNo;
    //1-借记卡 2-信用卡
    @NotNull(message = "银行卡类别不能为空")
    private ECardType cardType;
    //银行id
    @NotNull(message = "银行id不能为空")
    private Long bankId;

    private String bankLogo;

    public long getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(long bankCardId) {
        this.bankCardId = bankCardId;
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

    public String getBankCardName() {
        return bankCardName;
    }

    public void setBankCardName(String bankCardName) {
        this.bankCardName = bankCardName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
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

    public String getSubbranchName() {
        return subbranchName;
    }

    public void setSubbranchName(String subbranchName) {
        this.subbranchName = subbranchName;
    }

    public String getSubbranchNo() {
        return subbranchNo;
    }

    public void setSubbranchNo(String subbranchNo) {
        this.subbranchNo = subbranchNo;
    }

    public ECardType getCardType() {
        return cardType;
    }

    public void setCardType(ECardType cardType) {
        this.cardType = cardType;
    }

    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public String getBankLogo() {
        return bankLogo;
    }

    public void setBankLogo(String bankLogo) {
        this.bankLogo = bankLogo;
    }
}
