package com.xinyunlian.jinfu.qrcode.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.qrcode.enums.EProdCodeStatus;


import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2017年03月08日.
 */
public class EProdCodeStatusConverter implements AttributeConverter<EProdCodeStatus, String> {

    @Override
    public String convertToDatabaseColumn(EProdCodeStatus eProdCodeStatus) {
        return eProdCodeStatus.getCode();
    }

    @Override
    public EProdCodeStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EProdCodeStatus.class, dbData);
    }
}
