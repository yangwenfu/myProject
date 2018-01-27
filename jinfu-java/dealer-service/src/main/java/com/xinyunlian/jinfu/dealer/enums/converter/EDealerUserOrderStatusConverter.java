package com.xinyunlian.jinfu.dealer.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserOrderStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2016年08月26日.
 */
public class EDealerUserOrderStatusConverter implements AttributeConverter<EDealerUserOrderStatus, String> {

    @Override
    public String convertToDatabaseColumn(EDealerUserOrderStatus eDealerUserOrderStatus) {
        return eDealerUserOrderStatus.getCode();
    }

    @Override
    public EDealerUserOrderStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EDealerUserOrderStatus.class, dbData);
    }
}
