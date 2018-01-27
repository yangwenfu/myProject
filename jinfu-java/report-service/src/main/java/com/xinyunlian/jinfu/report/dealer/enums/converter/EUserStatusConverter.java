package com.xinyunlian.jinfu.report.dealer.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.report.dealer.enums.EUserStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by bright on 2017/1/4.
 */
public class EUserStatusConverter implements AttributeConverter<EUserStatus, String> {
    @Override
    public String convertToDatabaseColumn(EUserStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public EUserStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EUserStatus.class, dbData);
    }
}
