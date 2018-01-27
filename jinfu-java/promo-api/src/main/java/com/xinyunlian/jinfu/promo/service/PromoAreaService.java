package com.xinyunlian.jinfu.promo.service;

import com.xinyunlian.jinfu.promo.dto.PromoAreaDto;

import java.util.List;

/**
 * 促销地区Service
 * @author jll
 * @version 
 */

public interface PromoAreaService {

    void save(List<PromoAreaDto> promoAreaDtos);

    List<PromoAreaDto> getAreasByPromoId(Long promoId);

    void deleteByPromotionId(Long promoId);
}
