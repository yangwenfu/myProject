package com.xinyunlian.jinfu.risk.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.risk.enums.ERiskOrderType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class ERiskOrderTypeEnumConverter implements AttributeConverter<ERiskOrderType, String> {

    @Override
    public String convertToDatabaseColumn(ERiskOrderType attribute) {
        return attribute.getCode();
    }

    @Override
    public ERiskOrderType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ERiskOrderType.class, dbData);
    }
}