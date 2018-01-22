package com.xinyunlian.jinfu.exam.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.exam.enums.EExamType;

import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2017年05月02日.
 */
public class EExamTypeConverter implements AttributeConverter<EExamType, String> {

    @Override
    public String convertToDatabaseColumn(EExamType eExamType) {
        return eExamType.getCode();
    }

    @Override
    public EExamType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EExamType.class, dbData);
    }
}
