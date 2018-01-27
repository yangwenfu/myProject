package com.xinyunlian.jinfu.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.enums.ETrxType;

import javax.persistence.AttributeConverter;

/**
 * Created by cong on 2016/5/24.
 */
public class ETrxTypeConverter implements AttributeConverter<ETrxType, String> {

    @Override
    public String convertToDatabaseColumn(ETrxType attribute) {
        return attribute.getCode();
    }

    @Override
    public ETrxType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ETrxType.class, dbData);
    }
}
