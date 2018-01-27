package com.xinyunlian.jinfu.insurance.enums.converters;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.insurance.enums.EPerInsOrderStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by DongFC on 2016-09-21.
 */
public class EPerInsOrderStatusConverter implements AttributeConverter<EPerInsOrderStatus, String> {
    @Override
    public String convertToDatabaseColumn(EPerInsOrderStatus ePerInsOrderStatus) {
        return ePerInsOrderStatus.getCode();
    }

    @Override
    public EPerInsOrderStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EPerInsOrderStatus.class, dbData);
    }
}
