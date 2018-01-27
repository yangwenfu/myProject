package com.xinyunlian.jinfu.app.enums.converter;

import com.xinyunlian.jinfu.app.enums.EOperatingSystem;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;

/**
 * Created by DongFC on 2016-08-24.
 */
public class EOperatingSystemConverter implements AttributeConverter<EOperatingSystem, String> {

    @Override
    public String convertToDatabaseColumn(EOperatingSystem operatingSystem) {
        return operatingSystem.getCode();
    }

    @Override
    public EOperatingSystem convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EOperatingSystem.class, dbData);
    }
}
