package com.xinyunlian.jinfu.ad.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/1/6/0006.
 */
public class AdPicDto implements Serializable {

    private static final long serialVersionUID = 3454241584528106405L;
    private Long id;
    private Long adPosSizeId;
    private Long adId;
    private String picPath;
    private Integer picWidth;
    private Integer picHeight;

    private AdPosSizeDto adPosSizeDto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdPosSizeId() {
        return adPosSizeId;
    }

    public void setAdPosSizeId(Long adPosSizeId) {
        this.adPosSizeId = adPosSizeId;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public AdPosSizeDto getAdPosSizeDto() {
        return adPosSizeDto;
    }

    public void setAdPosSizeDto(AdPosSizeDto adPosSizeDto) {
        this.adPosSizeDto = adPosSizeDto;
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
