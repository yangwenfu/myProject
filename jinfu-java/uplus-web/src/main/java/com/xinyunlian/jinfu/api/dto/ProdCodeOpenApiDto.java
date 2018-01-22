package com.xinyunlian.jinfu.api.dto;

import com.xinyunlian.jinfu.api.validate.dto.OpenApiBaseDto;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by menglei on 2017-03-22.
 */
public class ProdCodeOpenApiDto extends OpenApiBaseDto {

    private static final long serialVersionUID = 506057658733074423L;

    @NotBlank(message = "商户id不能为空")
    private Long storeId;//商户id

    @NotBlank(message = "商品码地址不能为空")
    private String qrCodeUrl;//商品码地址

    @NotBlank(message = "订单号不能为空")
    private String orderNo;//订单号

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
