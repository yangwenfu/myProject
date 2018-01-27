package com.xinyunlian.jinfu.audit.enums.converter;

import com.xinyunlian.jinfu.audit.enums.EAuditStatus;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class ELoanAuditStatusEnumConverter implements AttributeConverter<EAuditStatus, String> {

    @Override
    public String convertToDatabaseColumn(EAuditStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public EAuditStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EAuditStatus.class, dbData);
    }
}