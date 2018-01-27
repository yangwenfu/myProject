package com.xinyunlian.jinfu.bank.enums.converter;

import com.xinyunlian.jinfu.bank.enums.ECardUsage;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;

/**
 * Created by cong on 2016/5/24.
 */
public class ECardUsageConverter implements AttributeConverter<ECardUsage, String> {

    @Override
    public String convertToDatabaseColumn( ECardUsage cardUsage) {
        return cardUsage.getCode();
    }

    @Override
    public  ECardUsage convertToEntityAttribute(String dbData) {
        return EnumHelper.translate( ECardUsage.class, dbData);
    }
}
