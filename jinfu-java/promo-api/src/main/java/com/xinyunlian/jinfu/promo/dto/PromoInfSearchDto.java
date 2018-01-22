package com.xinyunlian.jinfu.promo.dto;

import com.xinyunlian.jinfu.common.dto.PagingDto;
import com.xinyunlian.jinfu.promo.enums.EPlatform;
import com.xinyunlian.jinfu.promo.enums.EPromoInfStatus;
import com.xinyunlian.jinfu.promo.enums.EPromoInfType;

/**
 * Created by bright on 2016/11/21.
 */
public class PromoInfSearchDto extends PagingDto {
    private String name;
    private String prodId;
    private EPromoInfType type;
    private EPlatform platform;
    private EPromoInfStatus status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public EPromoInfType getType() {
        return type;
    }

    public void setType(EPromoInfType type) {
        this.type = type;
    }

    public EPlatform getPlatform() {
        return platform;
    }

    public void setPlatform(EPlatform platform) {
        this.platform = platform;
    }

    public EPromoInfStatus getStatus() {
        return status;
    }

    public void setStatus(EPromoInfStatus status) {
        this.status = status;
    }
}
