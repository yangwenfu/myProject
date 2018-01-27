package com.xinyunlian.jinfu.user.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.user.enums.ELabelType;

import javax.persistence.AttributeConverter;

/**
 * Created by cong on 2016/5/24.
 */
public class ELabelTypeConverter implements AttributeConverter<ELabelType, String> {

    @Override
    public String convertToDatabaseColumn(ELabelType arg) {
        return arg.getCode();
    }

    @Override
    public ELabelType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ELabelType.class, dbData);
    }
}
