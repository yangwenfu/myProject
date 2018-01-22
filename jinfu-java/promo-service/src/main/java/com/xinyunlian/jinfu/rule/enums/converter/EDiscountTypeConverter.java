package com.xinyunlian.jinfu.rule.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.rule.enums.EDiscountType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by jll on 2016/5/24.
 */
@Converter
public class EDiscountTypeConverter implements AttributeConverter<EDiscountType, String> {

    @Override
    public String convertToDatabaseColumn(EDiscountType attribute) {
        return attribute.getCode();
    }

    @Override
    public  EDiscountType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate( EDiscountType.class, dbData);
    }
}
