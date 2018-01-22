package com.xinyunlian.jinfu.system.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.system.enums.ESysConfigType;

import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2017/6/12.
 */
public class ESysConfigTypeConverter implements AttributeConverter<ESysConfigType, String> {

    @Override
    public String convertToDatabaseColumn(ESysConfigType sysConfigType) {
        return sysConfigType.getCode();
    }

    @Override
    public ESysConfigType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ESysConfigType.class, dbData);
    }
}
