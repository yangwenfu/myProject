package com.xinyunlian.jinfu.prod.dto;

import com.xinyunlian.jinfu.prod.enums.EShelfPlatform;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/1/22/0022.
 */
public class ProdShelfDto implements Serializable {
    private static final long serialVersionUID = -962002514812789806L;

    private Long id;

    private String prodId;

    private EShelfPlatform shelfPlatform;

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

    public EShelfPlatform getShelfPlatform() {
        return shelfPlatform;
    }

    public void setShelfPlatform(EShelfPlatform shelfPlatform) {
        this.shelfPlatform = shelfPlatform;
    }
}
