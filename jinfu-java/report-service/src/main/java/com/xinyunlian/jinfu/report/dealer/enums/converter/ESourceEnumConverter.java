package com.xinyunlian.jinfu.report.dealer.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.report.dealer.enums.ESource;

import javax.persistence.AttributeConverter;

/**
 * Created by bright on 2016/11/29.
 */
public class ESourceEnumConverter implements AttributeConverter<ESource, String>{
    @Override
    public String convertToDatabaseColumn(ESource attribute) {
        return attribute.getCode();
    }

    @Override
    public ESource convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ESource.class, dbData);
    }
}
