package com.xinyunlian.jinfu.report.dealer.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.report.dealer.enums.EDealerUserLogType;

import javax.persistence.AttributeConverter;

/**
 * Created by bright on 2016/11/29.
 */
public class EDealerUserLogTypeEnumConverter implements AttributeConverter<EDealerUserLogType, String>{
    @Override
    public String convertToDatabaseColumn(EDealerUserLogType attribute) {
        return attribute.getCode();
    }

    @Override
    public EDealerUserLogType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EDealerUserLogType.class, dbData);
    }
}
