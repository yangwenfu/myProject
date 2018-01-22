package com.xinyunlian.jinfu.qrcode.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by menglei on 2017年03月09日.
 */
public class ProdInfDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long prodId;

    private String name;

    private String sku;

    private String type;

    private String detailText;

    private Date createTs;

    public Long getProdId() {
        return prodId;
    }

    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDetailText() {
        return detailText;
    }

    public void setDetailText(String detailText) {
        this.detailText = detailText;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }
}
