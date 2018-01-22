package com.xinyunlian.jinfu.balance.enums.converter;

import com.xinyunlian.jinfu.balance.enums.EBalanceStatus;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class EBalanceStatusConverter implements AttributeConverter<EBalanceStatus, String> {

    @Override
    public String convertToDatabaseColumn(EBalanceStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public EBalanceStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EBalanceStatus.class, dbData);
    }
}