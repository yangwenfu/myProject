package com.xinyunlian.jinfu.audit.enums.converter;

import com.xinyunlian.jinfu.audit.enums.EAuditType;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class ELoanAuditTypeEnumConverter implements AttributeConverter<EAuditType, String> {

    @Override
    public String convertToDatabaseColumn(EAuditType attribute) {
        return attribute.getCode();
    }

    @Override
    public EAuditType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EAuditType.class, dbData);
    }
}