package com.xinyunlian.jinfu.prod.dto;

import java.io.Serializable;

/**
 * Created by DongFC on 2016-09-18.
 */
public class ProdTypeInfSerachDto implements Serializable {
    private static final long serialVersionUID = 4216817916805835388L;

    private Long prodTypeId;
    private String prodTypeName;
    private String prodTypeAlias;
    private Long parent;
    private String prodTypePath;

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public Long getProdTypeId() {
        return prodTypeId;
    }

    public void setProdTypeId(Long prodTypeId) {
        this.prodTypeId = prodTypeId;
    }

    public String getProdTypeName() {
        return prodTypeName;
    }

    public void setProdTypeName(String prodTypeName) {
        this.prodTypeName = prodTypeName;
    }

    public String getProdTypePath() {
        return prodTypePath;
    }

    public void setProdTypePath(String prodTypePath) {
        this.prodTypePath = prodTypePath;
    }

    public String getProdTypeAlias() {
        return prodTypeAlias;
    }

    public void setProdTypeAlias(String prodTypeAlias) {
        this.prodTypeAlias = prodTypeAlias;
    }
}
