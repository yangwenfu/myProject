package com.xinyunlian.jinfu.pay.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.pay.enums.EPrType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 
 * @author cheng
 *
 */
@Converter
public class EPrTypeEnumConverter implements AttributeConverter<EPrType, String> {

	@Override
    public String convertToDatabaseColumn(EPrType attribute) {
        return attribute.getCode();
    }

    @Override
    public EPrType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EPrType.class, dbData);
    }
	
}
