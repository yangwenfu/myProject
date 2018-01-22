package com.xinyunlian.jinfu.promo.state;

import com.xinyunlian.jinfu.common.enums.EErrorCode;
import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.promo.dto.PromoInfDto;
import com.xinyunlian.jinfu.promo.dto.PromotionDto;
import com.xinyunlian.jinfu.promo.enums.EPromoInfStatus;

import java.util.Date;

/**
 * Created by bright on 2016/11/22.
 */
public class ActiveState implements PromotionState {
    @Override
    public void on(PromoInfDto promoInfDto) {
        if(promoInfDto.getStatus().equals(EPromoInfStatus.INACTIVE)){
            if(promoInfDto.getEndDate().before(new Date())){
                promoInfDto.setStatus(EPromoInfStatus.FINISHED);
            } else {
                promoInfDto.setStatus(EPromoInfStatus.ACTIVE);
            }
            return;
        }
        throw new BizServiceException(EErrorCode.PROMO_OPERATION_NOT_ALLOW);
    }
}
