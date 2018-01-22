package com.xinyunlian.jinfu.dealer.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.dealer.enums.EDealerType;

import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2016年08月26日.
 */
    public class EDealerTypeConverter implements AttributeConverter<EDealerType, String> {

    @Override
    public String convertToDatabaseColumn(EDealerType eDealerStatus) {
        return eDealerStatus.getCode();
    }

    @Override
    public EDealerType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EDealerType.class, dbData);
    }
}
