package com.xinyunlian.jinfu.config.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.config.enums.ECategory;

import javax.persistence.AttributeConverter;

/**
 * Created by bright on 2017/1/6.
 */
public class ECategoryConverter implements AttributeConverter<ECategory, String> {
    @Override
    public String convertToDatabaseColumn(ECategory attribute) {
        return attribute.getCode();
    }

    @Override
    public ECategory convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ECategory.class, dbData);
    }
}
