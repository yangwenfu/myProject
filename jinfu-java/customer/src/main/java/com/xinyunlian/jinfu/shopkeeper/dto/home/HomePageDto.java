package com.xinyunlian.jinfu.shopkeeper.dto.home;

import com.xinyunlian.jinfu.ad.dto.AdFrontDto;
import com.xinyunlian.jinfu.creditline.dto.LoanUserCreditLineDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by King on 2017/1/23.
 */
public class HomePageDto {
    private LoanUserCreditLineDto loanCreditLineDto = new LoanUserCreditLineDto();
    //广告列表
    private List<AdFrontDto> adFrontDtos = new ArrayList<>();

    //品牌广告位
    private List<AdFrontDto> brandAdDtos = new ArrayList<>();

    //文章列表
    private List<ArticleDto> articleDtos = new ArrayList<>();

    //楼层列表
    private List<FloorDto> floorDto = new ArrayList<>();

    //公告列表
    private List<NoticeDto> noticeDtos = new ArrayList<>();

    public LoanUserCreditLineDto getLoanCreditLineDto() {
        return loanCreditLineDto;
    }

    public void setLoanCreditLineDto(LoanUserCreditLineDto loanCreditLineDto) {
        this.loanCreditLineDto = loanCreditLineDto;
    }

    public List<AdFrontDto> getAdFrontDtos() {
        return adFrontDtos;
    }

    public void setAdFrontDtos(List<AdFrontDto> adFrontDtos) {
        this.adFrontDtos = adFrontDtos;
    }

    public List<ArticleDto> getArticleDtos() {
        return articleDtos;
    }

    public void setArticleDtos(List<ArticleDto> articleDtos) {
        this.articleDtos = articleDtos;
    }

    public List<FloorDto> getFloorDto() {
        return floorDto;
    }

    public void setFloorDto(List<FloorDto> floorDto) {
        this.floorDto = floorDto;
    }

    public List<NoticeDto> getNoticeDtos() {
        return noticeDtos;
    }

    public void setNoticeDtos(List<NoticeDto> noticeDtos) {
        this.noticeDtos = noticeDtos;
    }

    public List<AdFrontDto> getBrandAdDtos() {
        return brandAdDtos;
    }

    public void setBrandAdDtos(List<AdFrontDto> brandAdDtos) {
        this.brandAdDtos = brandAdDtos;
    }
}
