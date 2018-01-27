package com.xinyunlian.jinfu.user.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.user.enums.EIncomeSource;

import javax.persistence.AttributeConverter;

/**
 * Created by jll on 2017/3/7.
 */
public class EIncomeSourceConverter implements AttributeConverter<EIncomeSource, String> {

    @Override
    public String convertToDatabaseColumn(EIncomeSource arg) {
        return arg.getCode();
    }

    @Override
    public EIncomeSource convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EIncomeSource.class, dbData);
    }
}
