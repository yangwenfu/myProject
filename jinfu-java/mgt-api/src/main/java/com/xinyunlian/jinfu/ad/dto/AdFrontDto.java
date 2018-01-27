package com.xinyunlian.jinfu.ad.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/1/6/0006.
 */
public class AdFrontDto implements Serializable {
    private static final long serialVersionUID = -395586149337943564L;

    private Long adPosId;

    private String adName;

    private Integer adPosWidth;

    private Integer adPosHeight;

    private String adUrl;

    private String picPath;

    private Integer picWidth;

    private Integer picHeight;

    public Long getAdPosId() {
        return adPosId;
    }

    public void setAdPosId(Long adPosId) {
        this.adPosId = adPosId;
    }

    public Integer getAdPosWidth() {
        return adPosWidth;
    }

    public void setAdPosWidth(Integer adPosWidth) {
        this.adPosWidth = adPosWidth;
    }

    public Integer getAdPosHeight() {
        return adPosHeight;
    }

    public void setAdPosHeight(Integer adPosHeight) {
        this.adPosHeight = adPosHeight;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public Integer getPicWidth() {
        return picWidth;
    }

    public void setPicWidth(Integer picWidth) {
        this.picWidth = picWidth;
    }

    public Integer getPicHeight() {
        return picHeight;
    }

    public void setPicHeight(Integer picHeight) {
        this.picHeight = picHeight;
    }
}
