package com.xinyunlian.jinfu.ad.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/1/6/0006.
 */

@Entity
@Table(name = "AD_PIC")
public class AdPicPo implements Serializable {

    private static final long serialVersionUID = -6606454776706577863L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "AD_POS_SIZE_ID")
    private Long adPosSizeId;

    @Column(name = "AD_ID")
    private Long adId;

    @Column(name = "PIC_PATH")
    private String picPath;

    @Column(name = "PIC_WIDTH")
    private Integer picWidth;

    @Column(name = "PIC_HEIGHT")
    private Integer picHeight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAdPosSizeId() {
        return adPosSizeId;
    }

    public void setAdPosSizeId(Long adPosSizeId) {
        this.adPosSizeId = adPosSizeId;
    }

    public Long getAdId() {
        return adId;
    }

    public void setAdId(Long adId) {
        this.adId = adId;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public Integer getPicWidth() {
        return picWidth;
    }

    public void setPicWidth(Integer picWidth) {
        this.picWidth = picWidth;
    }

    public Integer getPicHeight() {
        return picHeight;
    }

    public void setPicHeight(Integer picHeight) {
        this.picHeight = picHeight;
    }
}
