package com.xinyunlian.jinfu.prod.dto;

import com.xinyunlian.jinfu.area.dto.SysAreaInfDto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by DongFC on 2016-08-19.
 */
public class ProductDto implements Serializable {

    private static final long serialVersionUID = 574495165966030885L;

    private String prodId;

    private String picPath;

    private String absolutePicPath;

    private String prodTypePath;

    private String prodName;

    private String provider;

    private String prodAlias;

    private ProdTypeInfDto prodTypeInfDtoLv1;

    private ProdTypeInfDto prodTypeInfDtoLv2;

    private ProdTypeInfDto prodTypeInfDtoLv3;

    private List<SysAreaInfDto> sysAreaInfDtoList;

    private List<ProdShelfDto> prodShelfList;

    private List<ProdUserGroupDto> prodUserGroupList;

    private ProdAppDetailDto prodAppDetailDto;

    private List<ProdIndustryDto> prodIndustryList;

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public List<SysAreaInfDto> getSysAreaInfDtoList() {
        return sysAreaInfDtoList;
    }

    public void setSysAreaInfDtoList(List<SysAreaInfDto> sysAreaInfDtoList) {
        this.sysAreaInfDtoList = sysAreaInfDtoList;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getProdTypePath() {
        return prodTypePath;
    }

    public void setProdTypePath(String prodTypePath) {
        this.prodTypePath = prodTypePath;
    }

    public ProdTypeInfDto getProdTypeInfDtoLv1() {
        return prodTypeInfDtoLv1;
    }

    public void setProdTypeInfDtoLv1(ProdTypeInfDto prodTypeInfDtoLv1) {
        this.prodTypeInfDtoLv1 = prodTypeInfDtoLv1;
    }

    public ProdTypeInfDto getProdTypeInfDtoLv2() {
        return prodTypeInfDtoLv2;
    }

    public void setProdTypeInfDtoLv2(ProdTypeInfDto prodTypeInfDtoLv2) {
        this.prodTypeInfDtoLv2 = prodTypeInfDtoLv2;
    }

    public ProdTypeInfDto getProdTypeInfDtoLv3() {
        return prodTypeInfDtoLv3;
    }

    public void setProdTypeInfDtoLv3(ProdTypeInfDto prodTypeInfDtoLv3) {
        this.prodTypeInfDtoLv3 = prodTypeInfDtoLv3;
    }

    public String getProdAlias() {
        return prodAlias;
    }

    public void setProdAlias(String prodAlias) {
        this.prodAlias = prodAlias;
    }

    public List<ProdShelfDto> getProdShelfList() {
        return prodShelfList;
    }

    public void setProdShelfList(List<ProdShelfDto> prodShelfList) {
        this.prodShelfList = prodShelfList;
    }

    public List<ProdUserGroupDto> getProdUserGroupList() {
        return prodUserGroupList;
    }

    public void setProdUserGroupList(List<ProdUserGroupDto> prodUserGroupList) {
        this.prodUserGroupList = prodUserGroupList;
    }

    public ProdAppDetailDto getProdAppDetailDto() {
        return prodAppDetailDto;
    }

    public void setProdAppDetailDto(ProdAppDetailDto prodAppDetailDto) {
        this.prodAppDetailDto = prodAppDetailDto;
    }

    public String getAbsolutePicPath() {
        return absolutePicPath;
    }

    public void setAbsolutePicPath(String absolutePicPath) {
        this.absolutePicPath = absolutePicPath;
    }

    public List<ProdIndustryDto> getProdIndustryList() {
        return prodIndustryList;
    }

    public void setProdIndustryList(List<ProdIndustryDto> prodIndustryList) {
        this.prodIndustryList = prodIndustryList;
    }
}
