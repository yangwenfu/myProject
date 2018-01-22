package com.xinyunlian.jinfu.trade.enums.converters;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.trade.enums.ETradeStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2016年11月20日.
 */
public class ETradeStatusConverter implements AttributeConverter<ETradeStatus, String> {
    @Override
    public String convertToDatabaseColumn(ETradeStatus status) {
        return status.getCode();
    }

    @Override
    public ETradeStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ETradeStatus.class, dbData);
    }

}
