package com.xinyunlian.jinfu.pay.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.pay.enums.EMsgType;

import javax.persistence.AttributeConverter;

/**
 * Created by cong on 2016/5/24.
 */
public class EMsgTypeConverter implements AttributeConverter<EMsgType, String> {

    @Override
    public String convertToDatabaseColumn(EMsgType attribute) {
        return attribute.getCode();
    }

    @Override
    public EMsgType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EMsgType.class, dbData);
    }
}
