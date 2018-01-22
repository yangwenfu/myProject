package com.xinyunlian.jinfu.exam.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.exam.enums.EExamStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2017年05月02日.
 */
public class EExamStatusConverter implements AttributeConverter<EExamStatus, String> {

    @Override
    public String convertToDatabaseColumn(EExamStatus eExamStatus) {
        return eExamStatus.getCode();
    }

    @Override
    public EExamStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EExamStatus.class, dbData);
    }
}
