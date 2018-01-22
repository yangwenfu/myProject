package com.xinyunlian.jinfu.store.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.store.enums.EEstate;

import javax.persistence.AttributeConverter;

/**
 * Created by cong on 2016/5/24.
 */
public class EEstateConverter implements AttributeConverter<EEstate, String> {

    @Override
    public String convertToDatabaseColumn(EEstate estate) {
        return estate.getCode();
    }

    @Override
    public EEstate convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EEstate.class, dbData);
    }
}
