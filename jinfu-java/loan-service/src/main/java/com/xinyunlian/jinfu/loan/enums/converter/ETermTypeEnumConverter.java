package com.xinyunlian.jinfu.loan.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.loan.enums.ETermType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 
 * @author cheng
 *
 */
@Converter
public class ETermTypeEnumConverter implements AttributeConverter<ETermType, String> {

	@Override
    public String convertToDatabaseColumn(ETermType attribute) {
        return attribute.getCode();
    }

    @Override
    public ETermType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ETermType.class, dbData);
    }
	
}
