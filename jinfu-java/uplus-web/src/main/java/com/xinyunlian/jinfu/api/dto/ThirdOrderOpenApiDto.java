package com.xinyunlian.jinfu.api.dto;

import com.xinyunlian.jinfu.api.validate.dto.OpenApiBaseDto;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by menglei on 2017-01-05.
 */
public class ThirdOrderOpenApiDto extends OpenApiBaseDto {

    private static final long serialVersionUID = 506057658733074423L;

    @NotBlank(message = "商户id不能为空")
    private Long storeId;//商户id

    @NotBlank(message = "订单号不能为空")
    private String orderNo;//订单号

    @NotBlank(message = "供应商不能为空")
    private String supplier;//供应商

    @NotBlank(message = "订货平台不能为空")
    private String platform;//订货平台

    @NotBlank(message = "订货时间不能为空")
    private Date orderTime;//订货时间

    @NotBlank(message = "入库方式不能为空")
    private String storageMode;//入库方式

    @NotBlank(message = "入库时间不能为空")
    private Date storageTime;//入库时间

    @NotNull(message = "订单商品不能为空")
    private List<OrderProdOpenApiDto> orderProdList = new ArrayList<>();//订单商品

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getStorageMode() {
        return storageMode;
    }

    public void setStorageMode(String storageMode) {
        this.storageMode = storageMode;
    }

    public Date getStorageTime() {
        return storageTime;
    }

    public void setStorageTime(Date storageTime) {
        this.storageTime = storageTime;
    }

    public List<OrderProdOpenApiDto> getOrderProdList() {
        return orderProdList;
    }

    public void setOrderProdList(List<OrderProdOpenApiDto> orderProdList) {
        this.orderProdList = orderProdList;
    }
}
