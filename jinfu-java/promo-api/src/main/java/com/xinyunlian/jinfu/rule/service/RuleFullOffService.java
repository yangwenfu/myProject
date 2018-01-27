package com.xinyunlian.jinfu.rule.service;

import com.xinyunlian.jinfu.promo.dto.PromoRuleDto;
import com.xinyunlian.jinfu.rule.dto.RuleFullOffDto;

import java.math.BigDecimal;

/**
 * 满减Service
 * @author jll
 * @version 
 */

public interface RuleFullOffService {

    void save(RuleFullOffDto ruleFullOffDto);

    RuleFullOffDto findByPromoId(Long promoId);

    PromoRuleDto getRuleDto(Long promoId, BigDecimal amount);

}
