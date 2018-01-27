package com.xinyunlian.jinfu.user.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.user.enums.EOperationType;

import javax.persistence.AttributeConverter;

/**
 * Created by cong on 2016/5/24.
 */
public class EOperationLogConverter implements AttributeConverter<EOperationType, String> {

    @Override
    public String convertToDatabaseColumn(EOperationType operationType) {
        return operationType.name();
    }

    @Override
    public EOperationType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EOperationType.class, dbData);
    }
}
