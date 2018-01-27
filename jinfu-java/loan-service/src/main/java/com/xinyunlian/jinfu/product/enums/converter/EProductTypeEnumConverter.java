package com.xinyunlian.jinfu.product.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.product.enums.ELoanProductType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class EProductTypeEnumConverter implements AttributeConverter<ELoanProductType, String> {

	@Override
    public String convertToDatabaseColumn(ELoanProductType attribute) {
        return attribute.getCode();
    }

    @Override
    public ELoanProductType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ELoanProductType.class, dbData);
    }
	
}
