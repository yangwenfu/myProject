package com.xinyunlian.jinfu.ad.dto;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by DongFC on 2016-08-29.
 */
public class AdInfWebDto implements Serializable {
    private static final long serialVersionUID = 4142809017927303025L;

    private Long adId;

    @NotBlank(message = "广告名称不能为空")
    private String adName;

    @NotNull(message = "广告位不能为空")
    private Long adPosId;

    private Boolean display;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

    private String adUrl;

    private List<Long> adPosSizeIdList;

    private AdPositionDto adPositionDto;

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

    public Boolean getDisplay() {
        return display;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
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

    public AdPositionDto getAdPositionDto() {
        return adPositionDto;
    }

    public void setAdPositionDto(AdPositionDto adPositionDto) {
        this.adPositionDto = adPositionDto;
    }

    public List<Long> getAdPosSizeIdList() {
        return adPosSizeIdList;
    }

    public void setAdPosSizeIdList(List<Long> adPosSizeIdList) {
        this.adPosSizeIdList = adPosSizeIdList;
    }

    public String getAdUrl() {
        return adUrl;
    }

    public void setAdUrl(String adUrl) {
        this.adUrl = adUrl;
    }
}
