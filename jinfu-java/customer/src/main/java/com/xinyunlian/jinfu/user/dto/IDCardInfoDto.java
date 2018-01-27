package com.xinyunlian.jinfu.user.dto;

import java.io.Serializable;

/**
 * Created by King on 2017/2/24.
 */
public class IDCardInfoDto implements Serializable {
    private static final long serialVersionUID = -1043710265655043322L;

    private String name;

    private String idCard;

    private String frontFilePath;

    private String backFilePath;

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

    public String getFrontFilePath() {
        return frontFilePath;
    }

    public void setFrontFilePath(String frontFilePath) {
        this.frontFilePath = frontFilePath;
    }

    public String getBackFilePath() {
        return backFilePath;
    }

    public void setBackFilePath(String backFilePath) {
        this.backFilePath = backFilePath;
    }
}
