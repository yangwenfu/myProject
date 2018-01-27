package com.xinyunlian.jinfu.yunma.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.yunma.enums.EMemberIntoStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2017/7/19.
 */
public class EMemberIntoStatusConverter implements AttributeConverter<EMemberIntoStatus, String> {

    @Override
    public String convertToDatabaseColumn(EMemberIntoStatus status) {
        return status.getCode();
    }

    @Override
    public EMemberIntoStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EMemberIntoStatus.class, dbData);
    }
}
