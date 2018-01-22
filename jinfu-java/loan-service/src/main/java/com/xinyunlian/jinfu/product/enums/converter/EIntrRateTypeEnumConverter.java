package com.xinyunlian.jinfu.product.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.product.enums.EIntrRateType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class EIntrRateTypeEnumConverter implements AttributeConverter<EIntrRateType, String> {

    @Override
    public String convertToDatabaseColumn(EIntrRateType attribute) {
        return attribute.getCode();
    }

    @Override
    public EIntrRateType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EIntrRateType.class, dbData);
    }

}
