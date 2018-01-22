package com.xinyunlian.jinfu.loan.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.loan.enums.EApplStatus;
import com.xinyunlian.jinfu.log.enums.ELoanAuditLogType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class ELoanAuditLogTypeConverter implements AttributeConverter<ELoanAuditLogType, String> {

    @Override
    public String convertToDatabaseColumn(ELoanAuditLogType attribute) {
        return attribute.getCode();
    }

    @Override
    public ELoanAuditLogType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ELoanAuditLogType.class, dbData);
    }
}