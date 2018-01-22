package com.xinyunlian.jinfu.car.enums.converter;

import com.xinyunlian.jinfu.car.enums.ECarRegMode;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;

/**
 * Created by jll on 2016/5/24.
 */
public class ECarRegModeConverter implements AttributeConverter<ECarRegMode, String> {

    @Override
    public String convertToDatabaseColumn(ECarRegMode regMode) {
        return regMode.getCode();
    }

    @Override
    public ECarRegMode convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ECarRegMode.class, dbData);
    }
}
