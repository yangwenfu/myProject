package com.xinyunlian.jinfu.promo.dto;

import com.xinyunlian.jinfu.rule.dto.RuleCouponDto;
import com.xinyunlian.jinfu.rule.dto.RuleFirstDiscountDto;
import com.xinyunlian.jinfu.rule.dto.RuleFirstGiftDto;
import com.xinyunlian.jinfu.rule.dto.RuleFullOffDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bright on 2016/11/21.
 */
public class PromotionDto implements Serializable {
    private PromoInfDto promoInfDto;
    private RuleFirstDiscountDto ruleFirstDiscountDto;
    private List<RuleFirstGiftDto> ruleFirstGiftDto = new ArrayList<>();
    private RuleFullOffDto ruleFullOffDto;
    private RuleCouponDto ruleCouponDto;
    private List<PromoAreaDto> promoAreaDto = new ArrayList<>();
    private List<CompanyCostDto> companyCostDto = new ArrayList<>();
    private List<WhiteBlackUserDto> blackList = new ArrayList<>();
    private List<WhiteBlackUserDto> whiteList = new ArrayList<>();
    private Boolean whiteListExists = false;
    private Boolean blackListExists = false;

    public PromoInfDto getPromoInfDto() {
        return promoInfDto;
    }

    public void setPromoInfDto(PromoInfDto promoInfDto) {
        this.promoInfDto = promoInfDto;
    }

    public RuleFirstDiscountDto getRuleFirstDiscountDto() {
        return ruleFirstDiscountDto;
    }

    public void setRuleFirstDiscountDto(RuleFirstDiscountDto ruleFirstDiscountDto) {
        this.ruleFirstDiscountDto = ruleFirstDiscountDto;
    }

    public List<RuleFirstGiftDto> getRuleFirstGiftDto() {
        return ruleFirstGiftDto;
    }

    public void setRuleFirstGiftDto(List<RuleFirstGiftDto> ruleFirstGiftDto) {
        this.ruleFirstGiftDto = ruleFirstGiftDto;
    }

    public RuleFullOffDto getRuleFullOffDto() {
        return ruleFullOffDto;
    }

    public void setRuleFullOffDto(RuleFullOffDto ruleFullOffDto) {
        this.ruleFullOffDto = ruleFullOffDto;
    }

    public RuleCouponDto getRuleCouponDto() {
        return ruleCouponDto;
    }

    public void setRuleCouponDto(RuleCouponDto ruleCouponDto) {
        this.ruleCouponDto = ruleCouponDto;
    }

    public List<PromoAreaDto> getPromoAreaDto() {
        return promoAreaDto;
    }

    public void setPromoAreaDto(List<PromoAreaDto> promoAreaDto) {
        this.promoAreaDto = promoAreaDto;
    }

    public List<CompanyCostDto> getCompanyCostDto() {
        return companyCostDto;
    }

    public void setCompanyCostDto(List<CompanyCostDto> companyCostDto) {
        this.companyCostDto = companyCostDto;
    }

    public List<WhiteBlackUserDto> getBlackList() {
        return blackList;
    }

    public void setBlackList(List<WhiteBlackUserDto> blackList) {
        this.blackList = blackList;
    }

    public List<WhiteBlackUserDto> getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(List<WhiteBlackUserDto> whiteList) {
        this.whiteList = whiteList;
    }

    public Boolean getWhiteListExists() {
        return whiteListExists;
    }

    public void setWhiteListExists(Boolean whiteListExists) {
        this.whiteListExists = whiteListExists;
    }

    public Boolean getBlackListExists() {
        return blackListExists;
    }

    public void setBlackListExists(Boolean blackListExists) {
        this.blackListExists = blackListExists;
    }
}
