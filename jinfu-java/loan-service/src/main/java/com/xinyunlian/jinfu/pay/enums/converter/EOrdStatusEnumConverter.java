package com.xinyunlian.jinfu.pay.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.pay.enums.EOrdStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 
 * @author cheng
 *
 */
@Converter
public class EOrdStatusEnumConverter implements AttributeConverter<EOrdStatus, String> {

	@Override
    public String convertToDatabaseColumn(EOrdStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public EOrdStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EOrdStatus.class, dbData);
    }
	
}
