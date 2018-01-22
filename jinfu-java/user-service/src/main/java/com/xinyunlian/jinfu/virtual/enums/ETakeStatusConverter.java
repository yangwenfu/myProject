package com.xinyunlian.jinfu.virtual.enums;

import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;

/**
 * Created by cong on 2016/5/24.
 */
public class ETakeStatusConverter implements AttributeConverter<ETakeStatus, String> {

    @Override
    public String convertToDatabaseColumn(ETakeStatus arg) {
        return arg.getCode();
    }

    @Override
    public ETakeStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ETakeStatus.class, dbData);
    }
}
