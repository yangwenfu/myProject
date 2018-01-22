package com.xinyunlian.jinfu.rule.service;

import com.xinyunlian.jinfu.rule.dto.RuleFirstGiftDto;

import java.util.List;

/**
 * 首单有礼Service
 * @author jll
 * @version 
 */

public interface RuleFirstGiftService {

    void save(List<RuleFirstGiftDto> ruleFirstGiftDtos);

    List<RuleFirstGiftDto> findByPromoId(Long promoId);

    void deleteByPromoId(Long promoId);
}
