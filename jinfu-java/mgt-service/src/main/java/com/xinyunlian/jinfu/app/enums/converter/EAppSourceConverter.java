package com.xinyunlian.jinfu.app.enums.converter;

import com.xinyunlian.jinfu.app.enums.EAppSource;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;

/**
 * Created by DongFC on 2016-08-24.
 */
public class EAppSourceConverter implements AttributeConverter<EAppSource, String> {

    @Override
    public String convertToDatabaseColumn(EAppSource appSource) {
        return appSource.getCode();
    }

    @Override
    public EAppSource convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EAppSource.class, dbData);
    }
}
