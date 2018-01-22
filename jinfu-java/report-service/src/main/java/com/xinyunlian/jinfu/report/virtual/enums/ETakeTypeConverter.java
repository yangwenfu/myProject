package com.xinyunlian.jinfu.report.virtual.enums;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.report.virtual.enums.ETakeType;

import javax.persistence.AttributeConverter;

/**
 * Created by cong on 2016/5/24.
 */
public class ETakeTypeConverter implements AttributeConverter<ETakeType, String> {

    @Override
    public String convertToDatabaseColumn(ETakeType arg) {
        return arg.getCode();
    }

    @Override
    public ETakeType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ETakeType.class, dbData);
    }
}
