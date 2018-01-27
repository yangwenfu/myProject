package com.xinyunlian.jinfu.ad.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/1/6/0006.
 */
public class AdPosSizeDto implements Serializable{

    private static final long serialVersionUID = 8502683436080436102L;
    private Long id;

    private Long adPosId;

    private Integer adPosWidth;

    private Integer adPosHeight;

    private AdPicDto adPicDto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public AdPicDto getAdPicDto() {
        return adPicDto;
    }

    public void setAdPicDto(AdPicDto adPicDto) {
        this.adPicDto = adPicDto;
    }
}
