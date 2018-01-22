package com.xinyunlian.jinfu.product.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.product.enums.EIntrRateType;
import com.xinyunlian.jinfu.product.enums.EViolateType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class EViolateTypeEnumConverter implements AttributeConverter<EViolateType, String> {

    @Override
    public String convertToDatabaseColumn(EViolateType attribute) {
        return attribute.getCode();
    }

    @Override
    public EViolateType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EViolateType.class, dbData);
    }

}
