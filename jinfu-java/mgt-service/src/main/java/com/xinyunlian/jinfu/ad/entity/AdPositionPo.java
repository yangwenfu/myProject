package com.xinyunlian.jinfu.ad.entity;

import com.xinyunlian.jinfu.ad.enums.EAdType;
import com.xinyunlian.jinfu.ad.enums.EPosPlatform;
import com.xinyunlian.jinfu.ad.enums.EPosStatus;
import com.xinyunlian.jinfu.ad.enums.converter.EAdTypeConverter;
import com.xinyunlian.jinfu.ad.enums.converter.EPosPlatformConverter;
import com.xinyunlian.jinfu.ad.enums.converter.EPosStatusConverter;
import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * Created by DongFC on 2016-08-18.
 */

@Entity
@Table(name = "AD_POSITION")
public class AdPositionPo extends BaseMaintainablePo {

    private static final long serialVersionUID = 4980321387829022457L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POS_ID")
    private Long posId;

    @Column(name = "POS_NAME")
    private String posName;

    @Column(name = "AD_TYPE")
    @Convert(converter = EAdTypeConverter.class)
    private EAdType adType;

    @Column(name = "POS_PLATFORM")
    @Convert(converter = EPosPlatformConverter.class)
    private EPosPlatform posPlatform;

    @Column(name = "POS_DESC")
    private String posDesc;

    @Column(name = "POS_STATUS")
    @Convert(converter = EPosStatusConverter.class)
    private EPosStatus posStatus;

    @Column(name = "AD_NUM")
    private Integer adNum;

    @Column(name = "BG_PIC_PATH")
    private String bgPicPath;

    @Column(name = "X_AXIS")
    private String xAxis;

    @Column(name = "Y_AXIS")
    private String yAxis;

    @Column(name = "RESOLUTION_HEIGHT")
    private Integer resolutionHeight;

    @Column(name = "RESOLUTION_WIDTH")
    private Integer resolutionWidth;

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
