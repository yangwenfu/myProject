package com.xinyunlian.jinfu.dealer.entity;

import com.xinyunlian.jinfu.common.entity.BasePo;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserOrderSource;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserOrderStatus;
import com.xinyunlian.jinfu.dealer.enums.converter.EDealerUserOrderSourceConverter;
import com.xinyunlian.jinfu.dealer.enums.converter.EDealerUserOrderStatusConverter;
import com.xinyunlian.jinfu.user.entity.DealerUserPo;

import javax.persistence.*;

/**
 * Created by menglei on 2016年08月26日.
 */
@Entity
@Table(name = "dealer_user_order")
public class DealerUserOrderPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "DEALER_ID")
    private String dealerId;

    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "STORE_ID")
    private String storeId;

    @Column(name = "STORE_USER_ID")
    private String storeUserId;

    @Column(name = "PROD_ID")
    private String prodId;

    @Column(name = "ORDER_NO")
    private String orderNo;

    @Column(name = "STATUS")
    @Convert(converter = EDealerUserOrderStatusConverter.class)
    private EDealerUserOrderStatus status;

    @Column(name = "SOURCE")
    @Convert(converter = EDealerUserOrderSourceConverter.class)
    private EDealerUserOrderSource source;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DEALER_ID", insertable = false, updatable = false)
    private DealerPo dealerPo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    private DealerUserPo dealerUserPo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreUserId() {
        return storeUserId;
    }

    public void setStoreUserId(String storeUserId) {
        this.storeUserId = storeUserId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public EDealerUserOrderStatus getStatus() {
        return status;
    }

    public void setStatus(EDealerUserOrderStatus status) {
        this.status = status;
    }

    public EDealerUserOrderSource getSource() {
        return source;
    }

    public void setSource(EDealerUserOrderSource source) {
        this.source = source;
    }

    public DealerPo getDealerPo() {
        return dealerPo;
    }

    public void setDealerPo(DealerPo dealerPo) {
        this.dealerPo = dealerPo;
    }

    public DealerUserPo getDealerUserPo() {
        return dealerUserPo;
    }

    public void setDealerUserPo(DealerUserPo dealerUserPo) {
        this.dealerUserPo = dealerUserPo;
    }
}
