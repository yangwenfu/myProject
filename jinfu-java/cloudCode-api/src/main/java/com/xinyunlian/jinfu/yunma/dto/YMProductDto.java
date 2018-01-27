package com.xinyunlian.jinfu.yunma.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2017-01-05.
 */
public class YMProductDto implements Serializable {

    private static final long serialVersionUID = 574495165966030885L;

    private String prodId;

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
