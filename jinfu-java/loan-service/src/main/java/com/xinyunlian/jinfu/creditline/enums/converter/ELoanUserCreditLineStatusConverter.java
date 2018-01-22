package com.xinyunlian.jinfu.creditline.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.creditline.enums.ELoanUserCreditLineStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class ELoanUserCreditLineStatusConverter implements AttributeConverter<ELoanUserCreditLineStatus, String> {

    @Override
    public String convertToDatabaseColumn(ELoanUserCreditLineStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public ELoanUserCreditLineStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ELoanUserCreditLineStatus.class, dbData);
    }
}