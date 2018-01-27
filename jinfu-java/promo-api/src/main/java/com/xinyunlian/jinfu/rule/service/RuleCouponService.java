package com.xinyunlian.jinfu.rule.service;

import com.xinyunlian.jinfu.rule.dto.RuleCouponDto;

/**
 * 优惠券Service
 * @author jll
 * @version 
 */

public interface RuleCouponService {

 void save(RuleCouponDto ruleCouponDto);

 void delete(Long id);

 void deleteByPromoId(Long promoId);

 RuleCouponDto findByPromoId(Long promoId);

}
