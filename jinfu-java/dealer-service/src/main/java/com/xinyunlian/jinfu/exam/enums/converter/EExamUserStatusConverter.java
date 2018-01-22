package com.xinyunlian.jinfu.exam.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.exam.enums.EExamUserStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2017年05月02日.
 */
public class EExamUserStatusConverter implements AttributeConverter<EExamUserStatus, String> {

    @Override
    public String convertToDatabaseColumn(EExamUserStatus eExamUserStatus) {
        return eExamUserStatus.getCode();
    }

    @Override
    public EExamUserStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EExamUserStatus.class, dbData);
    }
}
