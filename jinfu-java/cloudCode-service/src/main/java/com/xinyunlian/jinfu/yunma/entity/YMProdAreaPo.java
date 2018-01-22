package com.xinyunlian.jinfu.yunma.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * Created by menglei on 2017-01-05.
 */
@Entity
@Table(name = "ym_prod_area")
public class YMProdAreaPo extends BaseMaintainablePo {
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

    public YMProdAreaPo() {
    }

    public YMProdAreaPo(Long areaId, String prodId) {
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
