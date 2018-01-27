package com.xinyunlian.jinfu.channel.enums.converter;

import com.xinyunlian.jinfu.channel.enums.EChannelStatus;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;

/**
 * Created by bright on 2016/11/18.
 */
public class EChannelStatusConverter implements AttributeConverter<EChannelStatus, String> {
    @Override
    public String convertToDatabaseColumn(EChannelStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public EChannelStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EChannelStatus.class, dbData);
    }
}
