package com.xinyunlian.jinfu.yunma.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.yunma.enums.EMemberStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by jll on 2016/5/24.
 */
public class EMemberStatusConverter implements AttributeConverter<EMemberStatus, String> {

    @Override
    public String convertToDatabaseColumn(EMemberStatus status) {
        return status.getCode();
    }

    @Override
    public EMemberStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EMemberStatus.class, dbData);
    }
}
