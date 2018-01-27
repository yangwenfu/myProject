package com.xinyunlian.jinfu.dealer.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserOrderSource;

import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2016年08月26日.
 */
public class EDealerUserOrderSourceConverter implements AttributeConverter<EDealerUserOrderSource, String> {

    @Override
    public String convertToDatabaseColumn(EDealerUserOrderSource eDealerUserOrderSource) {
        return eDealerUserOrderSource.getCode();
    }

    @Override
    public EDealerUserOrderSource convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EDealerUserOrderSource.class, dbData);
    }
}
