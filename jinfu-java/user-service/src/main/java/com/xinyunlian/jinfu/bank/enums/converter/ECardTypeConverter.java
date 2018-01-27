package com.xinyunlian.jinfu.bank.enums.converter;

import com.xinyunlian.jinfu.bank.enums.ECardType;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;

/**
 * Created by jll on 2016/5/24.
 */
public class ECardTypeConverter implements AttributeConverter<ECardType, String> {

    @Override
    public String convertToDatabaseColumn(ECardType cardType) {
        return cardType.getCode();
    }

    @Override
    public ECardType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ECardType.class, dbData);
    }
}
