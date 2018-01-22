package com.xinyunlian.jinfu.fintxhistory.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.fintxhistory.enums.ETxStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by DongFC on 2016-08-24.
 */
public class ETxStatusConverter implements AttributeConverter<ETxStatus, String> {

    @Override
    public String convertToDatabaseColumn(ETxStatus txStatus) {
        return txStatus.getCode();
    }

    @Override
    public ETxStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ETxStatus.class, dbData);
    }
}
