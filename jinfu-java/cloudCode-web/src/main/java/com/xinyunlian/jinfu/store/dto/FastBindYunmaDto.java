package com.xinyunlian.jinfu.store.dto;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * Created by menglei on 2017/8/30.
 */
public class FastBindYunmaDto implements Serializable {

    @NotBlank(message = "店铺id不能为空")
    private Long storeId;//店铺id

    @NotBlank(message = "银行卡类型不能为空")
    private Integer bankCardType;//银行卡类型

    @NotBlank(message = "银行卡id不能为空")
    private Long bankCardId;

    private String qrCodeNo;

    private String qrCodeUrl;

    private String referee;//推荐人

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Integer getBankCardType() {
        return bankCardType;
    }

    public void setBankCardType(Integer bankCardType) {
        this.bankCardType = bankCardType;
    }

    public String getQrCodeNo() {
        return qrCodeNo;
    }

    public void setQrCodeNo(String qrCodeNo) {
        this.qrCodeNo = qrCodeNo;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public Long getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(Long bankCardId) {
        this.bankCardId = bankCardId;
    }

    public String getReferee() {
        return referee;
    }

    public void setReferee(String referee) {
        this.referee = referee;
    }
}
