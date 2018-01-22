package com.xinyunlian.jinfu.ad.dto;

import com.xinyunlian.jinfu.ad.enums.EAdStatus;
import com.xinyunlian.jinfu.ad.enums.EAdValidStatus;
import com.xinyunlian.jinfu.ad.enums.EPosPlatform;
import com.xinyunlian.jinfu.common.dto.PagingDto;

import java.util.Date;

/**
 * Created by DongFC on 2016-08-22.
 */
public class AdInfSearchDto extends PagingDto<AdInfDto> {

    private static final long serialVersionUID = 3236652524606698084L;

    private Long adId;

    private String adName;

    private Long adPosId;

    private Integer adSort;

    private String picPath;

    private Date startDate;

    private Date endDate;

    private Boolean display;

    private EAdStatus adStatus;

    private EPosPlatform posPlatform;

    private EAdValidStatus adValidStatus;

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public String getAdName() {
        return adName;
    }

    public void setAdName(String adName) {
        this.adName = adName;
    }

    public Long getAdPosId() {
        return adPosId;
    }

    public void setAdPosId(Long adPosId) {
        this.adPosId = adPosId;
    }

    public Integer getAdSort() {
        return adSort;
    }

    public void setAdSort(Integer adSort) {
        this.adSort = adSort;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Boolean getDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
    }

    public EAdStatus getAdStatus() {
        return adStatus;
    }

    public void setAdStatus(EAdStatus adStatus) {
        this.adStatus = adStatus;
    }

    public EPosPlatform getPosPlatform() {
        return posPlatform;
    }

    public void setPosPlatform(EPosPlatform posPlatform) {
        this.posPlatform = posPlatform;
    }

    public EAdValidStatus getAdValidStatus() {
        return adValidStatus;
    }

    public void setAdValidStatus(EAdValidStatus adValidStatus) {
        this.adValidStatus = adValidStatus;
    }
}
