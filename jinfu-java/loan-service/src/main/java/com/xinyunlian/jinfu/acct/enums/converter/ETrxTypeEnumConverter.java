package com.xinyunlian.jinfu.acct.enums.converter;

import com.xinyunlian.jinfu.acct.enums.ETrxType;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 
 * @author cheng
 *
 */
@Converter
public class ETrxTypeEnumConverter implements AttributeConverter<ETrxType, String>{

	@Override
    public String convertToDatabaseColumn(ETrxType attribute) {
        return attribute.getCode();
    }

    @Override
    public ETrxType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ETrxType.class, dbData);
    }
	
}
