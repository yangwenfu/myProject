package com.xinyunlian.jinfu.promo.service;

import com.xinyunlian.jinfu.promo.dto.PromoInfDto;
import com.xinyunlian.jinfu.promo.dto.PromoInfSearchDto;
import com.xinyunlian.jinfu.promo.enums.EPromoInfStatus;

/**
 * 促销信息Service
 * @author jll
 * @version 
 */

public interface PromoInfService {

    Long savePromotion(PromoInfDto promotion);

    PromoInfDto getByPromoId(Long promoId);

    PromoInfSearchDto findPromoInf(PromoInfSearchDto searchDto);

    void setPromotionStatus(Long promotionId, EPromoInfStatus status);

    void finishJob();
}
