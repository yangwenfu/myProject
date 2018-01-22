package com.xinyunlian.jinfu.ad.entity;

import com.xinyunlian.jinfu.ad.enums.EAdStatus;
import com.xinyunlian.jinfu.ad.enums.converter.EAdStatusConverter;
import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by DongFC on 2016-08-18.
 */
@Entity
@Table(name = "AD_INF")
public class AdInfPo extends BaseMaintainablePo{

    private static final long serialVersionUID = 7930032937700276173L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AD_ID")
    private Long adId;

    @Column(name = "AD_NAME")
    private String adName;

    @Column(name = "AD_URL")
    private String adUrl;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "START_DATE")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "DISPLAY")
    private Boolean display;

    @Column(name = "AD_STATUS")
    @Convert(converter = EAdStatusConverter.class)
    private EAdStatus adStatus;

    @Column(name = "AD_POS_ID")
    private Long adPosId;

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

    public EAdStatus getAdStatus() {
        return adStatus;
    }

    public void setAdStatus(EAdStatus adStatus) {
        this.adStatus = adStatus;
    }

    public Long getAdPosId() {
        return adPosId;
    }

    public void setAdPosId(Long adPosId) {
        this.adPosId = adPosId;
    }
}
