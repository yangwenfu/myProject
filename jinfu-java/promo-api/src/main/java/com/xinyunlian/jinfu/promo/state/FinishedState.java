package com.xinyunlian.jinfu.promo.state;

import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.promo.dto.PromoInfDto;
import com.xinyunlian.jinfu.promo.enums.EPromoInfStatus;

/**
 * Created by bright on 2016/11/22.
 */
public class FinishedState implements PromotionState {
    @Override
    public void on(PromoInfDto promoInfDto) {
        if (promoInfDto.getStatus().equals(EPromoInfStatus.ACTIVE)){
            promoInfDto.setStatus(EPromoInfStatus.FINISHED);
            return;
        }
        throw new BizServiceException(EErrorCode.PROMO_OPERATION_NOT_ALLOW);
    }
}
