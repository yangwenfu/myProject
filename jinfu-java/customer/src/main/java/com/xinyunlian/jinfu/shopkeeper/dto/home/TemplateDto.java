package com.xinyunlian.jinfu.shopkeeper.dto.home;

/**
 * Created by King on 2017/1/25.
 */
public class TemplateDto {
    private String prodId;
    private String prodMode;
    private String name;
    private String code;
    //产品标题
    private String value;
    //副标题左
    private String dec;
    //副标题右
    private String decRight;
    //角标左
    private String tagLeftUrl;
    //角标右
    private String mark;
    private String picPath;
    private String url;
    private String detailUrl;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdMode() {
        return prodMode;
    }

    public void setProdMode(String prodMode) {
        this.prodMode = prodMode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDec() {
        return dec;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }

    public String getDecRight() {
        return decRight;
    }

    public void setDecRight(String decRight) {
        this.decRight = decRight;
    }

    public String getTagLeftUrl() {
        return tagLeftUrl;
    }

    public void setTagLeftUrl(String tagLeftUrl) {
        this.tagLeftUrl = tagLeftUrl;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }
}
