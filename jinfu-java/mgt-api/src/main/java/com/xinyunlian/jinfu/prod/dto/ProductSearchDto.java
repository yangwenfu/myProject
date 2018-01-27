package com.xinyunlian.jinfu.prod.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DongFC on 2016-08-23.
 */
public class ProductSearchDto implements Serializable {
    private static final long serialVersionUID = 8503726734903728548L;

    private String prodId;

    private String prodName;

    private String prodAlias;

    private List<Long> areaIds;

    private String prodTypePath;

    private Long prodTypeIdLv1;

    private Long prodTypeIdLv2;

    private Long prodTypeIdLv3;

    public List<Long> getAreaIds() {
        return areaIds;
    }

    public void setAreaIds(List<Long> areaIds) {
        this.areaIds = areaIds;
    }

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

    public String getProdTypePath() {
        return prodTypePath;
    }

    public void setProdTypePath(String prodTypePath) {
        this.prodTypePath = prodTypePath;
    }

    public Long getProdTypeIdLv1() {
        return prodTypeIdLv1;
    }

    public void setProdTypeIdLv1(Long prodTypeIdLv1) {
        this.prodTypeIdLv1 = prodTypeIdLv1;
    }

    public Long getProdTypeIdLv2() {
        return prodTypeIdLv2;
    }

    public void setProdTypeIdLv2(Long prodTypeIdLv2) {
        this.prodTypeIdLv2 = prodTypeIdLv2;
    }

    public Long getProdTypeIdLv3() {
        return prodTypeIdLv3;
    }

    public void setProdTypeIdLv3(Long prodTypeIdLv3) {
        this.prodTypeIdLv3 = prodTypeIdLv3;
    }

    public String getProdAlias() {
        return prodAlias;
    }

    public void setProdAlias(String prodAlias) {
        this.prodAlias = prodAlias;
    }
}
