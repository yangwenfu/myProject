package com.xinyunlian.jinfu.promo.service;

import com.xinyunlian.jinfu.promo.dto.CompanyCostDto;

import java.util.List;

/**
 * 主体信息Service
 * @author jll
 * @version 
 */

public interface CompanyCostService {

    void saveCostsPlan(List<CompanyCostDto> costsPlan);

    List<CompanyCostDto> getCostsPlanByPromoId(Long promoId);
}
