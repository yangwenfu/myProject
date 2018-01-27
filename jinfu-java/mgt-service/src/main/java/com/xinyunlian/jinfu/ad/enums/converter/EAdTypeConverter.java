package com.xinyunlian.jinfu.ad.enums.converter;

import com.xinyunlian.jinfu.ad.enums.EAdType;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;

/**
 * Created by DongFC on 2016-08-24.
 */
public class EAdTypeConverter implements AttributeConverter<EAdType, String> {

    @Override
    public String convertToDatabaseColumn(EAdType eAdType) {
        return eAdType.getCode();
    }

    @Override
    public EAdType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EAdType.class, dbData);
    }
}
