package com.xinyunlian.jinfu.promo.entity;

import com.xinyunlian.jinfu.common.entity.BasePo;
import com.xinyunlian.jinfu.promo.enmus.EPromoType;
import com.xinyunlian.jinfu.promo.enums.converter.EPromoTypeConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by JL on 2016/11/23.
 */
@Entity
@Table(name = "fp_loan_promo")
public class PromoPo extends BasePo{

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "LOAN_ID")
    private String loanId;

    @Column(name = "PROMO_ID")
    private Long promoId;

    @Column(name = "PROMO_TYPE")
    @Convert(converter = EPromoTypeConverter.class)
    private EPromoType promoType;

    @Column(name = "PROMO_VALUE")
    private BigDecimal promoValue;

    @Column(name = "PROMO_LEN")
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
