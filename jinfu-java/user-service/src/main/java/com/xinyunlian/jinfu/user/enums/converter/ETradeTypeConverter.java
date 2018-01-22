package com.xinyunlian.jinfu.user.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.user.enums.ETradeType;

import javax.persistence.AttributeConverter;

/**
 * Created by jll on 2016/5/24.
 */
public class ETradeTypeConverter implements AttributeConverter<ETradeType, String> {

    @Override
    public String convertToDatabaseColumn(ETradeType tradeType) {
        return tradeType.getCode();
    }

    @Override
    public ETradeType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ETradeType.class, dbData);
    }
}
