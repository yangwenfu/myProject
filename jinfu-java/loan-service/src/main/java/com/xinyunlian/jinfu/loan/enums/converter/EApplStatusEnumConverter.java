package com.xinyunlian.jinfu.loan.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.loan.enums.EApplStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class EApplStatusEnumConverter implements AttributeConverter<EApplStatus, String> {

    @Override
    public String convertToDatabaseColumn(EApplStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public EApplStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EApplStatus.class, dbData);
    }
}