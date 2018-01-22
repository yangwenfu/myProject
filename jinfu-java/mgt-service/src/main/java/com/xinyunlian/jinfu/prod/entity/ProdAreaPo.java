package com.xinyunlian.jinfu.prod.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by DongFC on 2016-08-19.
 */
@Entity
@Table(name = "prod_area")
public class ProdAreaPo implements Serializable {
    private static final long serialVersionUID = 2962130238021106783L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PROD_ID")
    private String prodId;

    @Column(name = "AREA_ID")
    private Long areaId;

    @Column(name = "AREA_TREE_PATH")
    private String areaTreePath;

    public ProdAreaPo() {
    }

    public ProdAreaPo(Long areaId, String prodId) {
        this.areaId = areaId;
        this.prodId = prodId;
    }

    public Long getAreaId() {
        return areaId;
    }

    public void setAreaId(Long areaId) {
        this.areaId = areaId;
    }

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

    public String getAreaTreePath() {
        return areaTreePath;
    }

    public void setAreaTreePath(String areaTreePath) {
        this.areaTreePath = areaTreePath;
    }
}
