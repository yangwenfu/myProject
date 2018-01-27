package com.xinyunlian.jinfu.order.enums.converters;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.order.enums.ECmccOrderTradeStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2016年11月20日.
 */
public class ECmccOrderTradeStatusConverter implements AttributeConverter<ECmccOrderTradeStatus, String> {
    @Override
    public String convertToDatabaseColumn(ECmccOrderTradeStatus eCmccOrderTradeStatus) {
        return eCmccOrderTradeStatus.getCode();
    }

    @Override
    public ECmccOrderTradeStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ECmccOrderTradeStatus.class, dbData);
    }
}
