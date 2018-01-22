package com.xinyunlian.jinfu.order.enums.converters;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.order.enums.ECmccOrderSource;

import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2016年11月20日.
 */
public class ECmccOrderSourceConverter implements AttributeConverter<ECmccOrderSource, String> {
    @Override
    public String convertToDatabaseColumn(ECmccOrderSource eCmccOrderSource) {
        return eCmccOrderSource.getCode();
    }

    @Override
    public ECmccOrderSource convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ECmccOrderSource.class, dbData);
    }
}
