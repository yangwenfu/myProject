package com.xinyunlian.jinfu.yunma.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.yunma.enums.EYmPayChannel;

import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2017/7/19.
 */
public class EYmPayChannelConverter implements AttributeConverter<EYmPayChannel, String> {

    @Override
    public String convertToDatabaseColumn(EYmPayChannel status) {
        return status.getCode();
    }

    @Override
    public EYmPayChannel convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EYmPayChannel.class, dbData);
    }
}
