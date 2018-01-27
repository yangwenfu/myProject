package com.xinyunlian.jinfu.loan.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.loan.enums.EApplChannel;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class EApplChannelEnumConverter implements AttributeConverter<EApplChannel, String> {

    @Override
    public String convertToDatabaseColumn(EApplChannel attribute) {
        return attribute.getCode();
    }

    @Override
    public EApplChannel convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EApplChannel.class, dbData);
    }
}