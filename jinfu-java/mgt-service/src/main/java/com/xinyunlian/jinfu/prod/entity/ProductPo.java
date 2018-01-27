package com.xinyunlian.jinfu.prod.entity;

import com.xinyunlian.jinfu.area.entity.SysAreaInfPo;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by DongFC on 2016-08-18.
 */
@Entity
@Table(name = "product")
public class ProductPo implements Serializable {
    private static final long serialVersionUID = -2167096528767937957L;

    @Id
    @Column(name = "PROD_ID")
    private String prodId;

    @Column(name = "PIC_PATH")
    private String picPath;

    @Column(name = "PROD_TYPE_PATH")
    private String prodTypePath;

    @Column(name = "PROD_NAME")
    private String prodName;

    @Column(name = "PROVIDER")
    private String provider;

    @Column(name = "PROD_ALIAS")
    private String prodAlias;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "prod_area", joinColumns = @JoinColumn(name = "PROD_ID"), inverseJoinColumns = @JoinColumn(name = "AREA_ID"))
    private List<SysAreaInfPo> sysAreaInfPoList;

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

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public List<SysAreaInfPo> getSysAreaInfPoList() {
        return sysAreaInfPoList;
    }

    public void setSysAreaInfPoList(List<SysAreaInfPo> sysAreaInfPoList) {
        this.sysAreaInfPoList = sysAreaInfPoList;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getProdTypePath() {
        return prodTypePath;
    }

    public void setProdTypePath(String prodTypePath) {
        this.prodTypePath = prodTypePath;
    }

    public String getProdAlias() {
        return prodAlias;
    }

    public void setProdAlias(String prodAlias) {
        this.prodAlias = prodAlias;
    }
}
