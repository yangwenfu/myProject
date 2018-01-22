package com.xinyunlian.jinfu.router.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.loan.enums.EApplChannel;
import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class EFinanceSourceTypeConverter implements AttributeConverter<EFinanceSourceType, String> {

    @Override
    public String convertToDatabaseColumn(EFinanceSourceType attribute) {
        return attribute.getCode();
    }

    @Override
    public EFinanceSourceType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EFinanceSourceType.class, dbData);
    }
}