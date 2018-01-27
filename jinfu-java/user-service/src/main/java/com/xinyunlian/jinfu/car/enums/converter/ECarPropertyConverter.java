package com.xinyunlian.jinfu.car.enums.converter;

import com.xinyunlian.jinfu.car.enums.ECarProperty;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;

/**
 * Created by jll on 2016/5/24.
 */
public class ECarPropertyConverter implements AttributeConverter<ECarProperty, String> {

    @Override
    public String convertToDatabaseColumn(ECarProperty property) {
        return property.getCode();
    }

    @Override
    public ECarProperty convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ECarProperty.class, dbData);
    }
}
