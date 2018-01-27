package com.xinyunlian.jinfu.dealer.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.dealer.enums.EDealerStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2016年08月26日.
 */
public class EDealerStatusConverter implements AttributeConverter<EDealerStatus, String> {

    @Override
    public String convertToDatabaseColumn(EDealerStatus eDealerStatus) {
        return eDealerStatus.getCode();
    }

    @Override
    public EDealerStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EDealerStatus.class, dbData);
    }
}
