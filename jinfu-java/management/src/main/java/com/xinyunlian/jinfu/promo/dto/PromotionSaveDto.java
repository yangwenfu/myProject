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
public class PromotionSaveDto implements Serializable {
    private PromoInfDto promoInfDto;
    private RuleFirstDiscountDto ruleFirstDiscountDto;
    private List<RuleFirstGiftDto> ruleFirstGiftDto = new ArrayList<>();
    private RuleFullOffDto ruleFullOffDto;
    private RuleCouponDto ruleCouponDto;
    private List<PromotionAreaDto> promoAreaDto = new ArrayList<>();
    private String whiteListFilePath;
    private String blackListFilePath;
    private Boolean whiteListExists = false;
    private Boolean blackListExists = false;
    private List<CompanyCostDto> companyCostDto = new ArrayList<>();

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

    public List<PromotionAreaDto> getPromoAreaDto() {
        return promoAreaDto;
    }

    public void setPromoAreaDto(List<PromotionAreaDto> promoAreaDto) {
        this.promoAreaDto = promoAreaDto;
    }

    public String getWhiteListFilePath() {
        return whiteListFilePath;
    }

    public void setWhiteListFilePath(String whiteListFilePath) {
        this.whiteListFilePath = whiteListFilePath;
    }

    public String getBlackListFilePath() {
        return blackListFilePath;
    }

    public void setBlackListFilePath(String blackListFilePath) {
        this.blackListFilePath = blackListFilePath;
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

    public List<CompanyCostDto> getCompanyCostDto() {
        return companyCostDto;
    }

    public void setCompanyCostDto(List<CompanyCostDto> companyCostDto) {
        this.companyCostDto = companyCostDto;
    }
}
