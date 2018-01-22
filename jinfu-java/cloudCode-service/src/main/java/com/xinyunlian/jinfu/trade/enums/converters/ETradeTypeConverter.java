package com.xinyunlian.jinfu.trade.enums.converters;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.trade.enums.ETradeType;

import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2017年06月01日.
 */
public class ETradeTypeConverter implements AttributeConverter<ETradeType, String> {
    @Override
    public String convertToDatabaseColumn(ETradeType status) {
        return status.getCode();
    }

    @Override
    public ETradeType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ETradeType.class, dbData);
    }

}
