package com.xinyunlian.jinfu.dealer.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserSubscribeType;

import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2017年08月03日.
 */
public class EDealerUserSubscribeTypeConverter implements AttributeConverter<EDealerUserSubscribeType, String> {

    @Override
    public String convertToDatabaseColumn(EDealerUserSubscribeType eDealerUserSubscribeType) {
        return eDealerUserSubscribeType.getCode();
    }

    @Override
    public EDealerUserSubscribeType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EDealerUserSubscribeType.class, dbData);
    }
}
