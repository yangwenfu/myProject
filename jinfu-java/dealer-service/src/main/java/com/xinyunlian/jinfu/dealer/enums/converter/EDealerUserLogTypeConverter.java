package com.xinyunlian.jinfu.dealer.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserLogType;

import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2016年08月26日.
 */
public class EDealerUserLogTypeConverter implements AttributeConverter<EDealerUserLogType, String> {

    @Override
    public String convertToDatabaseColumn(EDealerUserLogType eDealerUserLogType) {
        return eDealerUserLogType.getCode();
    }

    @Override
    public EDealerUserLogType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EDealerUserLogType.class, dbData);
    }
}
