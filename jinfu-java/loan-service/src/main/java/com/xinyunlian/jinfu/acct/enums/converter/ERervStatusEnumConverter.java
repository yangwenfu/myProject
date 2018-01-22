package com.xinyunlian.jinfu.acct.enums.converter;

import com.xinyunlian.jinfu.acct.enums.ERervStatus;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 
 * @author cheng
 *
 */
@Converter
public class ERervStatusEnumConverter implements AttributeConverter<ERervStatus, String>{

	@Override
    public String convertToDatabaseColumn(ERervStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public ERervStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ERervStatus.class, dbData);
    }
	
}
