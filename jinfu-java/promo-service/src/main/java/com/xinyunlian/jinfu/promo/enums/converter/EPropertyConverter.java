package com.xinyunlian.jinfu.promo.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.promo.enums.EProperty;

import javax.persistence.AttributeConverter;

/**
 * Created by bright on 2016/11/21.
 */
public class EPropertyConverter implements AttributeConverter<EProperty, String>{
    @Override
    public String convertToDatabaseColumn(EProperty attribute) {
        return attribute.getCode();
    }

    @Override
    public EProperty convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EProperty.class, dbData);
    }
}
