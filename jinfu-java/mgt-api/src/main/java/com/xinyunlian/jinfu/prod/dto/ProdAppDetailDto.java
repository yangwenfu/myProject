package com.xinyunlian.jinfu.prod.dto;

import java.io.Serializable;

/**
 * Created by dongfangchao on 2017/1/22/0022.
 */
public class ProdAppDetailDto implements Serializable {
    private static final long serialVersionUID = 6849829144098262978L;

    private Long id;

    private String prodId;

    private String prodDetail;

    private String prodUrl;

    private String bgPicPath;

    private String absoluteBgPicPath;

    private Boolean cfgRcmdFlag;

    private Integer cfgRcmdOrder;

    private Boolean cfgNewFlag;

    private Integer cfgNewOrder;

    private Boolean cfgHotFlag;

    private Integer cfgHotOrder;

    private String promoTag;

    private String absolutePromoTag;

    private String promoDesc;

    private String prodTitle;

    private String prodSubTitleLeft;

    private String prodSubTitleRight;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdDetail() {
        return prodDetail;
    }

    public void setProdDetail(String prodDetail) {
        this.prodDetail = prodDetail;
    }

    public String getProdUrl() {
        return prodUrl;
    }

    public void setProdUrl(String prodUrl) {
        this.prodUrl = prodUrl;
    }

    public String getBgPicPath() {
        return bgPicPath;
    }

    public void setBgPicPath(String bgPicPath) {
        this.bgPicPath = bgPicPath;
    }

    public String getAbsoluteBgPicPath() {
        return absoluteBgPicPath;
    }

    public void setAbsoluteBgPicPath(String absoluteBgPicPath) {
        this.absoluteBgPicPath = absoluteBgPicPath;
    }

    public Boolean getCfgRcmdFlag() {
        return cfgRcmdFlag;
    }

    public void setCfgRcmdFlag(Boolean cfgRcmdFlag) {
        this.cfgRcmdFlag = cfgRcmdFlag;
    }

    public Integer getCfgRcmdOrder() {
        return cfgRcmdOrder;
    }

    public void setCfgRcmdOrder(Integer cfgRcmdOrder) {
        this.cfgRcmdOrder = cfgRcmdOrder;
    }

    public Boolean getCfgNewFlag() {
        return cfgNewFlag;
    }

    public void setCfgNewFlag(Boolean cfgNewFlag) {
        this.cfgNewFlag = cfgNewFlag;
    }

    public Integer getCfgNewOrder() {
        return cfgNewOrder;
    }

    public void setCfgNewOrder(Integer cfgNewOrder) {
        this.cfgNewOrder = cfgNewOrder;
    }

    public Boolean getCfgHotFlag() {
        return cfgHotFlag;
    }

    public void setCfgHotFlag(Boolean cfgHotFlag) {
        this.cfgHotFlag = cfgHotFlag;
    }

    public Integer getCfgHotOrder() {
        return cfgHotOrder;
    }

    public void setCfgHotOrder(Integer cfgHotOrder) {
        this.cfgHotOrder = cfgHotOrder;
    }

    public String getPromoTag() {
        return promoTag;
    }

    public void setPromoTag(String promoTag) {
        this.promoTag = promoTag;
    }

    public String getAbsolutePromoTag() {
        return absolutePromoTag;
    }

    public void setAbsolutePromoTag(String absolutePromoTag) {
        this.absolutePromoTag = absolutePromoTag;
    }

    public String getPromoDesc() {
        return promoDesc;
    }

    public void setPromoDesc(String promoDesc) {
        this.promoDesc = promoDesc;
    }

    public String getProdTitle() {
        return prodTitle;
    }

    public void setProdTitle(String prodTitle) {
        this.prodTitle = prodTitle;
    }

    public String getProdSubTitleLeft() {
        return prodSubTitleLeft;
    }

    public void setProdSubTitleLeft(String prodSubTitleLeft) {
        this.prodSubTitleLeft = prodSubTitleLeft;
    }

    public String getProdSubTitleRight() {
        return prodSubTitleRight;
    }

    public void setProdSubTitleRight(String prodSubTitleRight) {
        this.prodSubTitleRight = prodSubTitleRight;
    }
}
