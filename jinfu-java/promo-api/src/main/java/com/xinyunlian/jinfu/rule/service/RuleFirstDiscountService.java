package com.xinyunlian.jinfu.rule.service;

import com.xinyunlian.jinfu.promo.dto.PromoRuleDto;
import com.xinyunlian.jinfu.rule.dto.RuleFirstDiscountDto;

/**
 * 首单折扣Service
 * @author jll
 * @version 
 */

public interface RuleFirstDiscountService {

    RuleFirstDiscountDto findByPromoId(Long promoId);

    void save(RuleFirstDiscountDto ruleFirstDiscountDto);

    PromoRuleDto getRuleDto(Long promoId);

    void deleteByPromoId(Long promoId);

}
