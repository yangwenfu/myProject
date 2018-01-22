package com.xinyunlian.jinfu.clerk.enums.converters;

import com.xinyunlian.jinfu.clerk.enums.EClerkApplyStatus;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2016年12月06日.
 */
public class EClerkApplyStatusConverter implements AttributeConverter<EClerkApplyStatus, String> {
    @Override
    public String convertToDatabaseColumn(EClerkApplyStatus eClerkApplyStatus) {
        return eClerkApplyStatus.getCode();
    }

    @Override
    public EClerkApplyStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EClerkApplyStatus.class, dbData);
    }
}
