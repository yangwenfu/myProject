package com.xinyunlian.jinfu.carbank.enums.converter;

import com.xinyunlian.jinfu.carbank.enums.ECbOrderStatus;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;

/**
 * Created by DongFC on 2016-08-24.
 */
public class ECbOrderStatusConverter implements AttributeConverter<ECbOrderStatus, String>{

    @Override
    public String convertToDatabaseColumn(ECbOrderStatus cbOrderStatus) {
        return cbOrderStatus.getCode();
    }

    @Override
    public ECbOrderStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ECbOrderStatus.class, dbData);
    }
}
