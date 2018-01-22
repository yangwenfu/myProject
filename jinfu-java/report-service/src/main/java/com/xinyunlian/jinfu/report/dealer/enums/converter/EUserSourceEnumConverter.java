package com.xinyunlian.jinfu.report.dealer.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.report.dealer.enums.EUserSource;

import javax.persistence.AttributeConverter;

/**
 * Created by bright on 2016/11/29.
 */
public class EUserSourceEnumConverter implements AttributeConverter<EUserSource, String>{
    @Override
    public String convertToDatabaseColumn(EUserSource attribute) {
        return attribute.getCode();
    }

    @Override
    public EUserSource convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EUserSource.class, dbData);
    }
}
