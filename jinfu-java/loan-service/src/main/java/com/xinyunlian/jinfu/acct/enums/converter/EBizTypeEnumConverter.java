package com.xinyunlian.jinfu.acct.enums.converter;

import com.xinyunlian.jinfu.acct.enums.EBizType;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 
 * @author cheng
 *
 */
@Converter
public class EBizTypeEnumConverter implements AttributeConverter<EBizType, String> {

	@Override
    public String convertToDatabaseColumn(EBizType attribute) {
        return attribute.getCode();
    }

    @Override
    public EBizType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EBizType.class, dbData);
    }
	
}
