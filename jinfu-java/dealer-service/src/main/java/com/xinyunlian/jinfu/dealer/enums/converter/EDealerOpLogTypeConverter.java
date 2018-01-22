package com.xinyunlian.jinfu.dealer.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.dealer.enums.EDealerOpLogType;

import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2017年05月09日.
 */
public class EDealerOpLogTypeConverter implements AttributeConverter<EDealerOpLogType, String> {

    @Override
    public String convertToDatabaseColumn(EDealerOpLogType eDealerOpLogType) {
        return eDealerOpLogType.getCode();
    }

    @Override
    public EDealerOpLogType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EDealerOpLogType.class, dbData);
    }
}
