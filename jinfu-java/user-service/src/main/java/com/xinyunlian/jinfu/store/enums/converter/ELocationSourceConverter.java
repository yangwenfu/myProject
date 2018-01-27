package com.xinyunlian.jinfu.store.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.store.enums.ELocationSource;

import javax.persistence.AttributeConverter;

/**
 * Created by cong on 2017/6/21.
 */
public class ELocationSourceConverter implements AttributeConverter<ELocationSource, String> {

    @Override
    public String convertToDatabaseColumn(ELocationSource eLocationSource) {
        return eLocationSource.getCode();
    }

    @Override
    public ELocationSource convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ELocationSource.class, dbData);
    }
}
