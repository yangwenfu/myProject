package com.xinyunlian.jinfu.qrcode.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;

/**
 * Created by menglei on 2017年03月14日.
 */
public class ProdInfSearchDto extends PagingDto<ProdInfDto> {

    private static final long serialVersionUID = 1L;

    private String name;

    private String sku;

    private String type;

    private String startCreateTs;

    private String endCreateTs;

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

    public String getStartCreateTs() {
        return startCreateTs;
    }

    public void setStartCreateTs(String startCreateTs) {
        this.startCreateTs = startCreateTs;
    }

    public String getEndCreateTs() {
        return endCreateTs;
    }

    public void setEndCreateTs(String endCreateTs) {
        this.endCreateTs = endCreateTs;
    }
}
