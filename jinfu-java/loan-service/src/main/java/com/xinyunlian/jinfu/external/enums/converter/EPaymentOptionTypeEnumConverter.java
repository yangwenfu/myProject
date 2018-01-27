package com.xinyunlian.jinfu.external.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.external.enums.PaymentOptionType;
import com.xinyunlian.jinfu.loan.enums.EApplOutType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class EPaymentOptionTypeEnumConverter implements AttributeConverter<PaymentOptionType, String> {

    @Override
    public String convertToDatabaseColumn(PaymentOptionType attribute) {
        return attribute.getCode();
    }

    @Override
    public PaymentOptionType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(PaymentOptionType.class, dbData);
    }
}