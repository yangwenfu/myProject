package com.xinyunlian.jinfu.user.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.user.enums.EMarryStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by cong on 2016/5/24.
 */
public class EMarryStatusConverter implements AttributeConverter<EMarryStatus, String> {

    @Override
    public String convertToDatabaseColumn(EMarryStatus arg) {
        return arg.getCode();
    }

    @Override
    public EMarryStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EMarryStatus.class, dbData);
    }
}
