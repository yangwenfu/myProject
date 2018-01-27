package com.xinyunlian.jinfu.external.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.loan.enums.EApplOutType;
import com.xinyunlian.jinfu.loan.enums.EApplStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class EApplOutTypeEnumConverter implements AttributeConverter<EApplOutType, String> {

    @Override
    public String convertToDatabaseColumn(EApplOutType attribute) {
        return attribute.getCode();
    }

    @Override
    public EApplOutType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EApplOutType.class, dbData);
    }
}