package com.xinyunlian.jinfu.store.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.store.enums.EBusinessLocation;

import javax.persistence.AttributeConverter;

/**
 * Created by cong on 2016/5/24.
 */
public class EBusinessLocationConverter implements AttributeConverter<EBusinessLocation, String> {

    @Override
    public String convertToDatabaseColumn( EBusinessLocation  businessLocation) {
        return businessLocation.getCode();
    }

    @Override
    public  EBusinessLocation convertToEntityAttribute(String dbData) {
        return EnumHelper.translate( EBusinessLocation.class, dbData);
    }
}
