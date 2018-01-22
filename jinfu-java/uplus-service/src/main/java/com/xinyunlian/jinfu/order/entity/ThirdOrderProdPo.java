package com.xinyunlian.jinfu.order.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * 第三方订单商品
 *
 * @author menglei
 */
@Entity
@Table(name = "third_order_prod")
public class ThirdOrderProdPo extends BaseMaintainablePo {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_PROD_ID")
    private Long orderProdId;

    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "STORE_ID")
    private Long storeId;

    @Column(name = "PROD_ID")
    private Long prodId;

    @Column(name = "PROD_NAME")
    private String prodName;

    @Column(name = "SKU")
    private String sku;

    @Column(name = "BOX_COUNT")
    private String boxCount;

    @Column(name = "PROD_COUNT")
    private String prodCount;

    @Column(name = "BIND_COUNT")
    private Long bindCount;

    public Long getOrderProdId() {
        return orderProdId;
    }

    public void setOrderProdId(Long orderProdId) {
        this.orderProdId = orderProdId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getProdId() {
        return prodId;
    }

    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getBoxCount() {
        return boxCount;
    }

    public void setBoxCount(String boxCount) {
        this.boxCount = boxCount;
    }

    public String getProdCount() {
        return prodCount;
    }

    public void setProdCount(String prodCount) {
        this.prodCount = prodCount;
    }

    public Long getBindCount() {
        return bindCount;
    }

    public void setBindCount(Long bindCount) {
        this.bindCount = bindCount;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }
}


