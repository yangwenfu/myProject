package com.xinyunlian.jinfu.user.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.user.enums.ESource;

import javax.persistence.AttributeConverter;

/**
 * Created by jll on 2016/5/24.
 */
public class ESourceConverter implements AttributeConverter<ESource, String> {

    @Override
    public String convertToDatabaseColumn(ESource source) {
        return source.getCode();
    }

    @Override
    public ESource convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ESource.class, dbData);
    }
}
