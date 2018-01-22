package com.xinyunlian.jinfu.ad.dto;

import com.xinyunlian.jinfu.ad.enums.EAdStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by DongFC on 2016-08-19.
 */
public class AdInfDto implements Serializable {

    private static final long serialVersionUID = -6830943820842946989L;

    private Long adId;

    private String adName;

    private Long adPosId;

    private String adUrl;

    private Date startDate;

    private Date endDate;

    private Boolean display;

    private EAdStatus adStatus;

    private AdPositionDto adPositionDto;

    private List<AdPicDto> adPicListAdd;

    private List<Long> adPicListDel;

    private List<AdPicDto> adPicListUpdate;

    private List<AdPosSizeDto> adPosSizeList;

    private List<Long> adPosSizeIdList;

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

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
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

    public AdPositionDto getAdPositionDto() {
        return adPositionDto;
    }

    public void setAdPositionDto(AdPositionDto adPositionDto) {
        this.adPositionDto = adPositionDto;
    }

    public EAdStatus getAdStatus() {
        return adStatus;
    }

    public void setAdStatus(EAdStatus adStatus) {
        this.adStatus = adStatus;
    }

    public List<AdPicDto> getAdPicListAdd() {
        return adPicListAdd;
    }

    public void setAdPicListAdd(List<AdPicDto> adPicListAdd) {
        this.adPicListAdd = adPicListAdd;
    }

    public List<AdPosSizeDto> getAdPosSizeList() {
        return adPosSizeList;
    }

    public void setAdPosSizeList(List<AdPosSizeDto> adPosSizeList) {
        this.adPosSizeList = adPosSizeList;
    }

    public List<Long> getAdPosSizeIdList() {
        return adPosSizeIdList;
    }

    public void setAdPosSizeIdList(List<Long> adPosSizeIdList) {
        this.adPosSizeIdList = adPosSizeIdList;
    }

    public List<Long> getAdPicListDel() {
        return adPicListDel;
    }

    public void setAdPicListDel(List<Long> adPicListDel) {
        this.adPicListDel = adPicListDel;
    }

    public List<AdPicDto> getAdPicListUpdate() {
        return adPicListUpdate;
    }

    public void setAdPicListUpdate(List<AdPicDto> adPicListUpdate) {
        this.adPicListUpdate = adPicListUpdate;
    }
}
