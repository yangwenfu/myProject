package com.xinyunlian.jinfu.user.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.user.enums.EHouseProperty;

import javax.persistence.AttributeConverter;

/**
 * Created by cong on 2016/5/24.
 */
public class EHousePropertyConverter implements AttributeConverter<EHouseProperty, String> {

    @Override
    public String convertToDatabaseColumn(EHouseProperty arg) {
        return arg.getCode();
    }

    @Override
    public EHouseProperty convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EHouseProperty.class, dbData);
    }
}
