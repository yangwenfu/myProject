package com.xinyunlian.jinfu.car.enums.converter;

import com.xinyunlian.jinfu.car.enums.ECarType;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;

/**
 * Created by jll on 2016/5/24.
 */
public class ECarTypeConverter implements AttributeConverter<ECarType, String> {

    @Override
    public String convertToDatabaseColumn(ECarType type) {
        return type.getCode();
    }

    @Override
    public ECarType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ECarType.class, dbData);
    }
}
