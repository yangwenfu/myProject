package com.xinyunlian.jinfu.api.dto;

import com.xinyunlian.jinfu.api.validate.dto.OpenApiBaseDto;

/**
 * Created by menglei on 2016年11月21日.
 */
public class ProdOpenApiDto extends OpenApiBaseDto {

    private static final long serialVersionUID = -1L;

    private String prodId;

    private String storeId;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }
}
