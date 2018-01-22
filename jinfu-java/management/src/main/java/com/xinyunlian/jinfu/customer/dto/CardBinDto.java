package com.xinyunlian.jinfu.customer.dto;

import com.xinyunlian.jinfu.common.util.excel.annotation.ExcelField;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 卡BinEntity
 *
 * @author jll
 */

public class CardBinDto {
    @ExcelField(title="BANKCODE",column = 2)
    private String bankCode;

    @NotBlank(message="不可为空")
    @ExcelField(title="BANKNAME",column = 3)
    private String bankName;

    @ExcelField(title="CARDNAME",column = 4)
    private String cardName;

    @Pattern(regexp="[0-9]\\d*",message="必须为数字")
    @ExcelField(title="BIN",column = 5)
    private String bin;

    @NotBlank(message="不可为空")
    @ExcelField(title="CARDTYPE",column = 6)
    private String cardType;

    @NotNull(message="不可为空")
    @ExcelField(title="CARDLENGTH",column = 7)
    private Integer numLength;


    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Integer getNumLength() {
        return numLength;
    }

    public void setNumLength(Integer numLength) {
        this.numLength = numLength;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }
}


