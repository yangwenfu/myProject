package com.xinyunlian.jinfu.order.enums.converters;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.order.enums.ECmccOrderPayStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2016年11月20日.
 */
public class ECmccOrderPayStatusConverter implements AttributeConverter<ECmccOrderPayStatus, String> {
    @Override
    public String convertToDatabaseColumn(ECmccOrderPayStatus eCmccOrderPayStatus) {
        return eCmccOrderPayStatus.getCode();
    }

    @Override
    public ECmccOrderPayStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ECmccOrderPayStatus.class, dbData);
    }
}
