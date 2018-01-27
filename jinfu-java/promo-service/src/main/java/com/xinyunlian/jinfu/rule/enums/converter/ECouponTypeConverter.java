package com.xinyunlian.jinfu.rule.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.rule.enums.ECouponType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by jll on 2016/5/24.
 */
@Converter
public class ECouponTypeConverter implements AttributeConverter<ECouponType, String> {

    @Override
    public String convertToDatabaseColumn(ECouponType attribute) {
        return attribute.getCode();
    }

    @Override
    public  ECouponType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate( ECouponType.class, dbData);
    }
}
