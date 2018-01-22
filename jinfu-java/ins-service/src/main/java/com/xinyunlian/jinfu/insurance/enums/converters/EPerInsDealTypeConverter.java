package com.xinyunlian.jinfu.insurance.enums.converters;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.insurance.enums.EPerInsDealType;

import javax.persistence.AttributeConverter;

/**
 * Created by DongFC on 2016-09-21.
 */
public class EPerInsDealTypeConverter implements AttributeConverter<EPerInsDealType, String> {
    @Override
    public String convertToDatabaseColumn(EPerInsDealType ePerInsDealType) {
        return ePerInsDealType.getCode();
    }

    @Override
    public EPerInsDealType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EPerInsDealType.class, dbData);
    }
}
