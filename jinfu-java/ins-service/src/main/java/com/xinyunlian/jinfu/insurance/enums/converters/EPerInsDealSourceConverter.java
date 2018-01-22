package com.xinyunlian.jinfu.insurance.enums.converters;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.insurance.enums.EPerInsDealSource;

import javax.persistence.AttributeConverter;

/**
 * Created by DongFC on 2016-09-21.
 */
public class EPerInsDealSourceConverter implements AttributeConverter<EPerInsDealSource, String> {
    @Override
    public String convertToDatabaseColumn(EPerInsDealSource ePerInsDealSource) {
        return ePerInsDealSource.getCode();
    }

    @Override
    public EPerInsDealSource convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EPerInsDealSource.class, dbData);
    }
}
