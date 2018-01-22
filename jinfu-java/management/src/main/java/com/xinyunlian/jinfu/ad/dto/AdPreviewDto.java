package com.xinyunlian.jinfu.ad.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/1/13/0013.
 */
public class AdPreviewDto implements Serializable {
    private static final long serialVersionUID = -36725349465371802L;

    private AdPositionDto adPositionDto;

    private AdPicDto adPicDto;

    public AdPositionDto getAdPositionDto() {
        return adPositionDto;
    }

    public void setAdPositionDto(AdPositionDto adPositionDto) {
        this.adPositionDto = adPositionDto;
    }

    public AdPicDto getAdPicDto() {
        return adPicDto;
    }

    public void setAdPicDto(AdPicDto adPicDto) {
        this.adPicDto = adPicDto;
    }
}
