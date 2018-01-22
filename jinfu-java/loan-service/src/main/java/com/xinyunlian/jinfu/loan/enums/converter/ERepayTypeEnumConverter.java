package com.xinyunlian.jinfu.loan.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.loan.enums.ERepayStatus;
import com.xinyunlian.jinfu.loan.enums.ERepayType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ERepayTypeEnumConverter implements AttributeConverter<ERepayType, String> {

	@Override
    public String convertToDatabaseColumn(ERepayType attribute) {
        return attribute.getCode();
    }

    @Override
    public ERepayType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ERepayType.class, dbData);
    }
	
}
