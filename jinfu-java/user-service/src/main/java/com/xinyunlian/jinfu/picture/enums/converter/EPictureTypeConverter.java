package com.xinyunlian.jinfu.picture.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.picture.enums.EPictureType;

import javax.persistence.AttributeConverter;

/**
 * Created by cong on 2016/5/24.
 */
public class EPictureTypeConverter implements AttributeConverter<EPictureType, String> {

    @Override
    public String convertToDatabaseColumn(EPictureType status) {
        return status.getCode();
    }

    @Override
    public EPictureType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EPictureType.class, dbData);
    }
}
