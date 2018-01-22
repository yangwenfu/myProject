package com.xinyunlian.jinfu.promo.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.promo.dto.StoreDto;
import com.xinyunlian.jinfu.promo.dto.UserCouponDto;
import com.xinyunlian.jinfu.promo.dto.UserDto;
import com.xinyunlian.jinfu.promo.enums.EUserCouponStatus;

import java.util.List;

/**
 * 个人优惠券Service
 *
 * @author jll
 */

public interface UserCouponService {

    UserCouponDto getUserCoupon(Long couponId);

    Long countUserCoupon(String userId, EUserCouponStatus status);

    void activeCoupon(String userId, String couponCode,String platform) throws BizServiceException;

    List<UserCouponDto> findByUserId(String userId, EUserCouponStatus status);

    List<UserCouponDto> findByUserId(String userId);

    List<UserCouponDto> getUsable(UserDto user,List<StoreDto> stores,int prodTypeId);

    void useCoupon(UserDto user, Long couponId) throws BizServiceException;

    void overdueJob();

    void giveCoupon(String value);

}
