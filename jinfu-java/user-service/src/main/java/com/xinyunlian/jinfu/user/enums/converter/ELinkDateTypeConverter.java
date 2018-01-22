package com.xinyunlian.jinfu.user.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.user.enums.ELinkDateType;

import javax.persistence.AttributeConverter;

/**
 * Created by cong on 2016/5/24.
 */
public class ELinkDateTypeConverter implements AttributeConverter<ELinkDateType, String> {

    @Override
    public String convertToDatabaseColumn(ELinkDateType arg) {
        return arg.getCode();
    }

    @Override
    public ELinkDateType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ELinkDateType.class, dbData);
    }
}
