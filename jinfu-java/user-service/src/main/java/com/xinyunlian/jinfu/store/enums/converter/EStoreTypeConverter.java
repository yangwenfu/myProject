package com.xinyunlian.jinfu.store.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.store.enums.EStoreType;

import javax.persistence.AttributeConverter;

/**
 * Created by jll on 2016/5/24.
 */
public class EStoreTypeConverter implements AttributeConverter<EStoreType, String> {

    @Override
    public String convertToDatabaseColumn(EStoreType type) {
        return type.getCode();
    }

    @Override
    public EStoreType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EStoreType.class, dbData);
    }
}
