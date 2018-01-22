package com.xinyunlian.jinfu.rule.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.rule.enums.EOffType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by jll 2016/5/24.
 */
@Converter
public class EOffTypeConverter implements AttributeConverter<EOffType, String> {

    @Override
    public String convertToDatabaseColumn(EOffType offType) {
        return offType.name();
    }

    @Override
    public EOffType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EOffType.class, dbData);
    }
}
