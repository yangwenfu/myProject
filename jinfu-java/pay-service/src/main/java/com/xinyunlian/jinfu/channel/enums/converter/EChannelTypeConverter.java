package com.xinyunlian.jinfu.channel.enums.converter;

import com.xinyunlian.jinfu.channel.enums.EChannelType;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;

/**
 * Created by bright on 2016/11/18.
 */
public class EChannelTypeConverter implements AttributeConverter<EChannelType, String> {
    @Override
    public String convertToDatabaseColumn(EChannelType attribute) {
        return attribute.getCode();
    }

    @Override
    public EChannelType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EChannelType.class, dbData);
    }
}
