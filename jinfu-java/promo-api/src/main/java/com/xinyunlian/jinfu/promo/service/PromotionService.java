package com.xinyunlian.jinfu.promo.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.promo.dto.PromoInfSearchDto;
import com.xinyunlian.jinfu.promo.dto.PromotionDto;
import com.xinyunlian.jinfu.promo.dto.WhiteBlackUserDto;
import com.xinyunlian.jinfu.promo.enums.ERecordType;

import java.util.List;

/**
 * Created by bright on 2016/11/22.
 */
public interface PromotionService {
    void savePromotion(PromotionDto promotion) throws BizServiceException;

    PromoInfSearchDto search(PromoInfSearchDto searchDto);

    PromotionDto getByPromotionId(Long promotionId);

    void activePromotion(Long promotionId) throws BizServiceException;

    void invalidPromotion(Long promotionId) throws BizServiceException;

    void finishPromotion(Long promotionId) throws BizServiceException;

    void deletePromotion(Long promotionId) throws BizServiceException;

    List<WhiteBlackUserDto> getWhiteBlackRecords(Long promotionId, ERecordType type);

    void finishJob();
}
