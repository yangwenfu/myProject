package com.xinyunlian.jinfu.ad.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/1/6/0006.
 */
@Entity
@Table(name = "AD_POS_SIZE")
public class AdPosSizePo implements Serializable{
    private static final long serialVersionUID = 5792151099206433347L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "AD_POS_ID")
    private Long adPosId;

    @Column(name = "AD_POS_WIDTH")
    private Integer adPosWidth;

    @Column(name = "AD_POS_HEIGHT")
    private Integer adPosHeight;

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
}
