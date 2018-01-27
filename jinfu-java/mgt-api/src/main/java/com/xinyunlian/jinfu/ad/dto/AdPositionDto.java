package com.xinyunlian.jinfu.ad.dto;

import com.xinyunlian.jinfu.ad.enums.EAdType;
import com.xinyunlian.jinfu.ad.enums.EPosPlatform;
import com.xinyunlian.jinfu.ad.enums.EPosStatus;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DongFC on 2016-08-19.
 */
public class AdPositionDto implements Serializable {

    private static final long serialVersionUID = -5818116569976354883L;

    private Long posId;

    private String posName;

    private EAdType adType;

    private EPosPlatform posPlatform;

    private Integer adNum;

    private String posDesc;

    private EPosStatus posStatus;

    private String bgPicPath;

    private String xAxis;

    private String yAxis;

    private Integer resolutionHeight;

    private Integer resolutionWidth;

    private List<AdPosSizeDto> adPosSizeList;

    private List<AdPosSizeDto> adPosSizeListDel;

    private List<AdPosSizeDto> adPosSizeListAdd;

    private List<AdPosSizeDto> adPosSizeListUpdate;

    public Long getPosId() {
        return posId;
    }

    public void setPosId(Long posId) {
        this.posId = posId;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }

    public EAdType getAdType() {
        return adType;
    }

    public void setAdType(EAdType adType) {
        this.adType = adType;
    }

    public EPosPlatform getPosPlatform() {
        return posPlatform;
    }

    public void setPosPlatform(EPosPlatform posPlatform) {
        this.posPlatform = posPlatform;
    }

    public String getPosDesc() {
        return posDesc;
    }

    public void setPosDesc(String posDesc) {
        this.posDesc = posDesc;
    }

    public EPosStatus getPosStatus() {
        return posStatus;
    }

    public void setPosStatus(EPosStatus posStatus) {
        this.posStatus = posStatus;
    }

    public Integer getAdNum() {
        return adNum;
    }

    public void setAdNum(Integer adNum) {
        this.adNum = adNum;
    }

    public List<AdPosSizeDto> getAdPosSizeList() {
        return adPosSizeList;
    }

    public void setAdPosSizeList(List<AdPosSizeDto> adPosSizeList) {
        this.adPosSizeList = adPosSizeList;
    }

    public List<AdPosSizeDto> getAdPosSizeListDel() {
        return adPosSizeListDel;
    }

    public void setAdPosSizeListDel(List<AdPosSizeDto> adPosSizeListDel) {
        this.adPosSizeListDel = adPosSizeListDel;
    }

    public List<AdPosSizeDto> getAdPosSizeListAdd() {
        return adPosSizeListAdd;
    }

    public void setAdPosSizeListAdd(List<AdPosSizeDto> adPosSizeListAdd) {
        this.adPosSizeListAdd = adPosSizeListAdd;
    }

    public List<AdPosSizeDto> getAdPosSizeListUpdate() {
        return adPosSizeListUpdate;
    }

    public void setAdPosSizeListUpdate(List<AdPosSizeDto> adPosSizeListUpdate) {
        this.adPosSizeListUpdate = adPosSizeListUpdate;
    }

    public String getBgPicPath() {
        return bgPicPath;
    }

    public void setBgPicPath(String bgPicPath) {
        this.bgPicPath = bgPicPath;
    }

    public String getxAxis() {
        return xAxis;
    }

    public void setxAxis(String xAxis) {
        this.xAxis = xAxis;
    }

    public String getyAxis() {
        return yAxis;
    }

    public void setyAxis(String yAxis) {
        this.yAxis = yAxis;
    }

    public Integer getResolutionHeight() {
        return resolutionHeight;
    }

    public void setResolutionHeight(Integer resolutionHeight) {
        this.resolutionHeight = resolutionHeight;
    }

    public Integer getResolutionWidth() {
        return resolutionWidth;
    }

    public void setResolutionWidth(Integer resolutionWidth) {
        this.resolutionWidth = resolutionWidth;
    }
}
