package com.xinyunlian.jinfu.report.dealer.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.report.dealer.enums.EDealerType;

import javax.persistence.AttributeConverter;

/**
 * Created by bright on 2016/11/29.
 */
public class EDealerTypeEnumConverter implements AttributeConverter<EDealerType, String>{
    @Override
    public String convertToDatabaseColumn(EDealerType attribute) {
        return attribute.getCode();
    }

    @Override
    public EDealerType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EDealerType.class, dbData);
    }
}
