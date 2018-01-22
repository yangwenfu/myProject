package com.xinyunlian.jinfu.qrcode.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2017年03月09日.
 */
public class ProdCodeCountDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long orderId;

    private Integer bindCount;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getBindCount() {
        return bindCount;
    }

    public void setBindCount(Integer bindCount) {
        this.bindCount = bindCount;
    }
}
