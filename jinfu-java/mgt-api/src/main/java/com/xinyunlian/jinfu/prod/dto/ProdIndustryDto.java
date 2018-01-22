package com.xinyunlian.jinfu.prod.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/5/17/0017.
 */
public class ProdIndustryDto implements Serializable{

    private static final long serialVersionUID = 2820654015496504624L;

    private Long id;

    private String prodId;

    private String indMcc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getIndMcc() {
        return indMcc;
    }

    public void setIndMcc(String indMcc) {
        this.indMcc = indMcc;
    }
}
