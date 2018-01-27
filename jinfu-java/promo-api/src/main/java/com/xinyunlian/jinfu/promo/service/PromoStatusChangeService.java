package com.xinyunlian.jinfu.promo.service;

import com.xinyunlian.jinfu.promo.dto.PromoInfDto;

/**
 * Created by King on 2016/11/21.
 */
public interface PromoStatusChangeService {
    void active(PromoInfDto promoInfDto);

    void invalid(PromoInfDto promoInfDto);

    void finish(PromoInfDto promoInfDto);

    void delete(PromoInfDto promoInfDto);
}
