package com.xinyunlian.jinfu.store.dto.resp;

import java.io.Serializable;

/**
 * @author king
 */
public class OCRRespDto implements Serializable{

    private String name;

    private String idCard;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
