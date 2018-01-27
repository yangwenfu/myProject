package com.xinyunlian.jinfu.prod.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * Created by dongfangchao on 2017/1/22/0022.
 */
@Entity
@Table(name = "prod_app_detail")
public class ProdAppDetailPo extends BaseMaintainablePo {
    private static final long serialVersionUID = 6849829144098262978L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PROD_ID")
    private String prodId;

    @Column(name = "PROD_DETAIL")
    private String prodDetail;

    @Column(name = "PROD_URL")
    private String prodUrl;

    @Column(name = "BG_PIC_PATH")
    private String bgPicPath;

    @Column(name = "CFG_RCMD_FLAG")
    private Boolean cfgRcmdFlag;

    @Column(name = "CFG_RCMD_ORDER")
    private Integer cfgRcmdOrder;

    @Column(name = "CFG_NEW_FLAG")
    private Boolean cfgNewFlag;

    @Column(name = "CFG_NEW_ORDER")
    private Integer cfgNewOrder;

    @Column(name = "CFG_HOT_FLAG")
    private Boolean cfgHotFlag;

    @Column(name = "CFG_HOT_ORDER")
    private Integer cfgHotOrder;

    @Column(name = "PROMO_TAG")
    private String promoTag;

    @Column(name = "PROMO_DESC")
    private String promoDesc;

    @Column(name = "PROD_TITLE")
    private String prodTitle;

    @Column(name = "PROD_SUB_TITLE_LEFT")
    private String prodSubTitleLeft;

    @Column(name = "PROD_SUB_TITLE_RIGHT")
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
