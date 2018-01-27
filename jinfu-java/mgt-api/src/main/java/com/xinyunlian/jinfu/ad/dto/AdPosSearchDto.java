package com.xinyunlian.jinfu.ad.dto;

import com.xinyunlian.jinfu.ad.enums.EAdType;
import com.xinyunlian.jinfu.ad.enums.EPosPlatform;
import com.xinyunlian.jinfu.ad.enums.EPosStatus;
import com.xinyunlian.jinfu.common.dto.PagingDto;

/**
 * Created by dongfangchao on 2016/12/21/0021.
 */
public class AdPosSearchDto extends PagingDto<AdPositionDto> {

    private static final long serialVersionUID = 1493566586138695650L;
    private Long posId;

    private String posName;

    private EAdType adType;

    private String posWidth;

    private String posHeight;

    private EPosPlatform posPlatform;

    private String posDesc;

    private EPosStatus posStatus;

    private Integer posOrder;

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

    public String getPosWidth() {
        return posWidth;
    }

    public void setPosWidth(String posWidth) {
        this.posWidth = posWidth;
    }

    public String getPosHeight() {
        return posHeight;
    }

    public void setPosHeight(String posHeight) {
        this.posHeight = posHeight;
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

    public Integer getPosOrder() {
        return posOrder;
    }

    public void setPosOrder(Integer posOrder) {
        this.posOrder = posOrder;
    }
}
