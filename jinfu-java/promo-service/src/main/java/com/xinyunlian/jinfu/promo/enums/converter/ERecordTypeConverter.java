package com.xinyunlian.jinfu.promo.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.promo.enums.ERecordType;

import javax.persistence.AttributeConverter;

/**
 * Created by bright on 2016/11/21.
 */
public class ERecordTypeConverter implements AttributeConverter<ERecordType, String>{
    @Override
    public String convertToDatabaseColumn(ERecordType attribute) {
        return attribute.getCode();
    }

    @Override
    public ERecordType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ERecordType.class, dbData);
    }
}
