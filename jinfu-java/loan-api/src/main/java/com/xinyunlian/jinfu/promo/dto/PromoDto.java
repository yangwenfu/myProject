package com.xinyunlian.jinfu.promo.dto;

import com.xinyunlian.jinfu.promo.enmus.EPromoType;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by JL on 2016/11/21.
 */
public class PromoDto implements Serializable {

    private Long id;

    private String loanId;

    private Long promoId;

    private EPromoType promoType;

    private BigDecimal promoValue;

    private Integer promoLen;

    public EPromoType getPromoType() {
        return promoType;
    }

    public void setPromoType(EPromoType promoType) {
        this.promoType = promoType;
    }

    public BigDecimal getPromoValue() {
        return promoValue;
    }

    public void setPromoValue(BigDecimal promoValue) {
        this.promoValue = promoValue;
    }

    public Integer getPromoLen() {
        return promoLen;
    }

    public void setPromoLen(Integer promoLen) {
        this.promoLen = promoLen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public Long getPromoId() {
        return promoId;
    }

    public void setPromoId(Long promoId) {
        this.promoId = promoId;
    }
}
