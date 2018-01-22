package com.xinyunlian.jinfu.uplus.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2017年03月21日.
 */
public class ProdCodeBindDto implements Serializable {

    private static final long serialVersionUID = -1L;

    private Long orderProdId;

    private String qrCodeUrl;

    public Long getOrderProdId() {
        return orderProdId;
    }

    public void setOrderProdId(Long orderProdId) {
        this.orderProdId = orderProdId;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }
}
