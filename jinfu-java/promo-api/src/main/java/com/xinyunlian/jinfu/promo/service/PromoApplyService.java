package com.xinyunlian.jinfu.promo.service;

import com.xinyunlian.jinfu.promo.dto.OrderDto;
import com.xinyunlian.jinfu.promo.dto.PromoRuleDto;
import com.xinyunlian.jinfu.promo.dto.StoreDto;
import com.xinyunlian.jinfu.promo.dto.UserDto;

import java.util.List;

/**
 * Created by King on 2016/11/23.
 */
public interface PromoApplyService {
    PromoRuleDto gain(UserDto user, List<StoreDto> stores, OrderDto orderDto);

    int checkBlackList(Long promoId, UserDto user, List<StoreDto> stores);

    int checkWhiteList(Long promoId, UserDto user, List<StoreDto> stores);

    int checkArea(Long promoId, List<StoreDto> stores);

}
