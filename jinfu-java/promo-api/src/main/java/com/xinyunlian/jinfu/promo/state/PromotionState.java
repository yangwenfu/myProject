package com.xinyunlian.jinfu.promo.state;

import com.xinyunlian.jinfu.promo.dto.PromoInfDto;
import com.xinyunlian.jinfu.promo.dto.PromotionDto;

/**
 * Created by bright on 2016/11/22.
 */
public interface PromotionState {
    public void on(PromoInfDto promoInfDto);
}
