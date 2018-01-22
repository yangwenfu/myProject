package com.xinyunlian.jinfu.audit.enums.converter;

import com.xinyunlian.jinfu.audit.enums.EAuditNoteType;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class ELoanAuditNoteTypeEnumConverter implements AttributeConverter<EAuditNoteType, String> {

    @Override
    public String convertToDatabaseColumn(EAuditNoteType attribute) {
        return attribute.getCode();
    }

    @Override
    public EAuditNoteType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EAuditNoteType.class, dbData);
    }
}