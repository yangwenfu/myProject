package com.xinyunlian.jinfu.external.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.loan.enums.EApplOutAuditType;
import com.xinyunlian.jinfu.loan.enums.EApplOutType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class EApplOutAuditTypeEnumConverter implements AttributeConverter<EApplOutAuditType, String> {

    @Override
    public String convertToDatabaseColumn(EApplOutAuditType attribute) {
        return attribute.getCode();
    }

    @Override
    public EApplOutAuditType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EApplOutAuditType.class, dbData);
    }
}