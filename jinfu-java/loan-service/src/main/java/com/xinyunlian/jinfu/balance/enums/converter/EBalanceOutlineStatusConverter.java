package com.xinyunlian.jinfu.balance.enums.converter;

import com.xinyunlian.jinfu.balance.enums.EBalanceOutlineStatus;
import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.loan.enums.EApplChannel;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class EBalanceOutlineStatusConverter implements AttributeConverter<EBalanceOutlineStatus, String> {

    @Override
    public String convertToDatabaseColumn(EBalanceOutlineStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public EBalanceOutlineStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EBalanceOutlineStatus.class, dbData);
    }
}