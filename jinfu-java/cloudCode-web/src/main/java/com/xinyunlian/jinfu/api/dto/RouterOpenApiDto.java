package com.xinyunlian.jinfu.api.dto;

import com.xinyunlian.jinfu.api.validate.dto.OpenApiBaseDto;

/**
 * Created by menglei on 2017/1/5.
 */
public class RouterOpenApiDto extends OpenApiBaseDto {

    private String userAgent;

    private String qrCodeNo;

    private String amount;

    private String userId;

    private String cost;

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getQrCodeNo() {
        return qrCodeNo;
    }

    public void setQrCodeNo(String qrCodeNo) {
        this.qrCodeNo = qrCodeNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
