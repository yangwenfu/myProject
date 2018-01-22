package com.xinyunlian.jinfu.fintxhistory.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.fintxhistory.enums.ETxType;

import javax.persistence.AttributeConverter;

/**
 * Created by DongFC on 2016-08-24.
 */
public class ETxTypeConverter implements AttributeConverter<ETxType, String> {

    @Override
    public String convertToDatabaseColumn(ETxType txType) {
        return txType.getCode();
    }

    @Override
    public ETxType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ETxType.class, dbData);
    }
}
