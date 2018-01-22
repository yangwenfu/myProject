package com.xinyunlian.jinfu.promo.controller;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.dto.ResultDto;
import com.xinyunlian.jinfu.common.dto.ResultDtoFactory;
import com.xinyunlian.jinfu.common.security.SecurityContext;
import com.xinyunlian.jinfu.promo.dto.AllCouponDto;
import com.xinyunlian.jinfu.promo.dto.CouponDto;
import com.xinyunlian.jinfu.promo.dto.UserCouponDto;
import com.xinyunlian.jinfu.promo.enums.EUserCouponStatus;
import com.xinyunlian.jinfu.promo.service.CouponService;
import com.xinyunlian.jinfu.promo.service.UserCouponService;
import com.xinyunlian.jinfu.rule.enums.ECouponType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.c;

/**
 * Created by King on 2017/3/20.
 */
@Controller
@RequestMapping(value = "promo/coupon")
public class CouponController {
    @Autowired
    private UserCouponService userCouponService;
    @Autowired
    private CouponService couponService;

    /**
     * 激活优惠券
     * @return
     */
    @RequestMapping(value = "/active", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> active(@RequestParam String couponCode) {
        userCouponService.activeCoupon(SecurityContext.getCurrentUserId(),couponCode,"loan,");
        return  ResultDtoFactory.toAck("获取成功");
    }

    /**
     * 获取个人优惠券
     * @return
     */
    @RequestMapping(value = "/getCoupon", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getCoupon() {
        String userId = SecurityContext.getCurrentUserId();
        AllCouponDto allCouponDto = new AllCouponDto();

        allCouponDto.setUnusedCount(userCouponService.countUserCoupon(userId,EUserCouponStatus.UNUSED));
        allCouponDto.setUsedCount(userCouponService.countUserCoupon(userId,EUserCouponStatus.USED));
        allCouponDto.setOverdueCount(userCouponService.countUserCoupon(userId,EUserCouponStatus.OVERDUE));

        allCouponDto.setUnusedCoupons(couponService
                .setCouponDto(userCouponService.findByUserId(userId,EUserCouponStatus.UNUSED)));

        allCouponDto.setUsedCoupons(couponService
                .setCouponDto(userCouponService.findByUserId(userId,EUserCouponStatus.USED)));

        allCouponDto.setOverdueCoupons(couponService
                .setCouponDto(userCouponService.findByUserId(userId,EUserCouponStatus.OVERDUE)));

        return  ResultDtoFactory.toAck("获取成功",allCouponDto);
    }

    /**
     * 获取个人优惠券
     * @return
     */
    @RequestMapping(value = "/getCouponCount", method = RequestMethod.GET)
    @ResponseBody
    public ResultDto<Object> getCouponCount() {
        List<UserCouponDto> userCouponDtos = userCouponService
                .findByUserId(SecurityContext.getCurrentUserId(), EUserCouponStatus.UNUSED);
        long count = 0;
        if(!CollectionUtils.isEmpty(userCouponDtos)){
            count = userCouponDtos.size();
        }
        return  ResultDtoFactory.toAck("获取成功",count);
    }


}
