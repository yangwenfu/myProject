package com.xinyunlian.jinfu.loan.dto.aitz;

import java.io.Serializable;

/**
 * @author willwang
 */
public class ATZSureDto implements Serializable{

    /**
     * 银行卡绑定页面
     */
    private String bindCardUrl;

    /**
     * 绑定成功页面
     */
    private String successUrl;

    /**
     * 是否已经有卡
     */
    private Boolean hasCard;

    public Boolean getHasCard() {
        return hasCard;
    }

    public void setHasCard(Boolean hasCard) {
        this.hasCard = hasCard;
    }

    public String getBindCardUrl() {
        return bindCardUrl;
    }

    public void setBindCardUrl(String bindCardUrl) {
        this.bindCardUrl = bindCardUrl;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }
}
