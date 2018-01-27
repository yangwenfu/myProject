package com.xinyunlian.jinfu.app.enums.converter;

import com.xinyunlian.jinfu.app.enums.EDataType;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2016-12-14.
 */
public class EDataTypeConverter implements AttributeConverter<EDataType, String> {

    @Override
    public String convertToDatabaseColumn(EDataType eDataType) {
        return eDataType.getCode();
    }

    @Override
    public EDataType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EDataType.class, dbData);
    }
}
