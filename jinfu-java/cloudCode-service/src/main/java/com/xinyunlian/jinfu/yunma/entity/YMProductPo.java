package com.xinyunlian.jinfu.yunma.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * Created by menglei on 2017-01-05.
 */
@Entity
@Table(name = "ym_product")
public class YMProductPo extends BaseMaintainablePo {
    private static final long serialVersionUID = -2167096528767937957L;

    @Id
    @Column(name = "PROD_ID")
    private String prodId;

    @Column(name = "PROD_NAME")
    private String prodName;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

}
