package com.xinyunlian.jinfu.prod.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by DongFC on 2016-09-18.
 */
@Entity
@Table(name = "prod_type_inf")
public class ProdTypeInfPo implements Serializable {
    private static final long serialVersionUID = -9214741110658374658L;

    @Id
    @Column(name = "PROD_TYPE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prodTypeId;

    @Column(name = "PROD_TYPE_CODE")
    private String prodTypeCode;

    @Column(name = "PROD_TYPE_NAME")
    private String prodTypeName;

    @Column(name = "PROD_TYPE_ALIAS")
    private String prodTypeAlias;

    @Column(name="PARENT")
    private Long parent;

    @Column(name = "PROD_TYPE_PATH")
    private String prodTypePath;

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

    public Long getParent() {
        return parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    public String getProdTypeCode() {
        return prodTypeCode;
    }

    public void setProdTypeCode(String prodTypeCode) {
        this.prodTypeCode = prodTypeCode;
    }

    public String getProdTypeAlias() {
        return prodTypeAlias;
    }

    public void setProdTypeAlias(String prodTypeAlias) {
        this.prodTypeAlias = prodTypeAlias;
    }
}
