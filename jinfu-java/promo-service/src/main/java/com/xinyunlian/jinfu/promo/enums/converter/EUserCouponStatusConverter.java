package com.xinyunlian.jinfu.promo.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.promo.enums.EUserCouponStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by jll on 2016/5/24.
 */
@Converter
public class EUserCouponStatusConverter implements AttributeConverter<EUserCouponStatus, String> {

    @Override
    public String convertToDatabaseColumn(EUserCouponStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public  EUserCouponStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate( EUserCouponStatus.class, dbData);
    }
}
