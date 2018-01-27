package com.xinyunlian.jinfu.user.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.user.enums.ESalaryMode;

import javax.persistence.AttributeConverter;

/**
 * Created by jll on 2017/3/7.
 */
public class ESalaryModeConverter implements AttributeConverter<ESalaryMode, String> {

    @Override
    public String convertToDatabaseColumn(ESalaryMode arg) {
        return arg.getCode();
    }

    @Override
    public ESalaryMode convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ESalaryMode.class, dbData);
    }
}
