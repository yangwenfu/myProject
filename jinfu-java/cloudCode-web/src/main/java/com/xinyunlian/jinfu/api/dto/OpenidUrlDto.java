package com.xinyunlian.jinfu.api.dto;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by menglei on 2017年09月05日.
 */
public class OpenidUrlDto {

    private static final long serialVersionUID = 4810036887617955539L;

    @NotBlank(message = "url不能为空")
    private String url;

    @NotBlank(message = "云码不能为空")
    private String qrCodeNo;

    private String cost;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQrCodeNo() {
        return qrCodeNo;
    }

    public void setQrCodeNo(String qrCodeNo) {
        this.qrCodeNo = qrCodeNo;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

}
