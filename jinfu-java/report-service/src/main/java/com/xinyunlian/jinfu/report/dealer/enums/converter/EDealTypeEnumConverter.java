package com.xinyunlian.jinfu.report.dealer.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.report.dealer.enums.EDealType;

import javax.persistence.AttributeConverter;

/**
 * Created by bright on 2016/11/29.
 */
public class EDealTypeEnumConverter implements AttributeConverter<EDealType, String>{
    @Override
    public String convertToDatabaseColumn(EDealType attribute) {
        return attribute.getCode();
    }

    @Override
    public EDealType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EDealType.class, dbData);
    }
}
