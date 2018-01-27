package com.xinyunlian.jinfu.promo.service;

import com.xinyunlian.jinfu.common.converter.ConverterService;
import com.xinyunlian.jinfu.common.util.DateHelper;
import com.xinyunlian.jinfu.prod.dto.ProductDto;
import com.xinyunlian.jinfu.prod.service.ProdService;
import com.xinyunlian.jinfu.promo.dto.CouponDto;
import com.xinyunlian.jinfu.promo.dto.UserCouponDto;
import com.xinyunlian.jinfu.rule.enums.ECouponType;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by King on 2017/3/27.
 */
@Service
public class CouponService {
    @Autowired
    private ProdService prodService;

    public List<CouponDto> setCouponDto(List<UserCouponDto> userCouponDtos){
        List<CouponDto> couponDtos = new ArrayList<>();

        userCouponDtos.forEach(userCouponDto -> {
            CouponDto couponDto = ConverterService.convert(userCouponDto,CouponDto.class);
            if(userCouponDto.getAdded() != null && userCouponDto.getAdded()){
                couponDto.setAddedNote("可叠加其他优惠活动");
            }else{
                couponDto.setAddedNote("不可叠加其他优惠活动");
            }
            couponDto.setMinimumNote("满" + userCouponDto.getMinimum().longValue() + "元可用");

            if(BigDecimal.valueOf(userCouponDto.getPrice().intValue())
                    .compareTo(userCouponDto.getPrice())==0){
                couponDto.setPrice(String.valueOf(userCouponDto.getPrice().intValue()));
            }else{
                couponDto.setPrice(String.valueOf(userCouponDto.getPrice()));
            }


            if(userCouponDto.getCouponType() == ECouponType.INTEREST){
                couponDto.setCouponType("免息券：仅减免还款金额中的利息");
            }

            ProductDto productDto = prodService.getProdById(userCouponDto.getProdId());
            if(productDto != null){
                String prodName = !StringUtils.isEmpty(productDto.getProdAlias())
                        ? productDto.getProdAlias() : productDto.getProdName();
                couponDto.setProdNameNote("仅" + prodName + "产品可用");
            }
            if(DateHelper.betweenDaysNew(new Date(),userCouponDto.getValidEndDate()) <= 7){
                couponDto.setBeOverdue(true);
            }
            couponDtos.add(couponDto);
        });

        return couponDtos;

    }
}
