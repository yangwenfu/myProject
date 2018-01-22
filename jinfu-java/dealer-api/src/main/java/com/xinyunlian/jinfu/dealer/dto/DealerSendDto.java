package com.xinyunlian.jinfu.dealer.dto;

import java.io.Serializable;

/**
 * Created by menglei on 2016年09月19日.
 */
public class DealerSendDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String sendId;

    private String dealerId;

    private String storeId;

    private String content;

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
