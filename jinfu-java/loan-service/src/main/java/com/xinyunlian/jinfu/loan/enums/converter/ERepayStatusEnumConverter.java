package com.xinyunlian.jinfu.loan.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.loan.enums.ERepayStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ERepayStatusEnumConverter implements AttributeConverter<ERepayStatus, String> {

	@Override
    public String convertToDatabaseColumn(ERepayStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public ERepayStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ERepayStatus.class, dbData);
    }
	
}
