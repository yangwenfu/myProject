package com.xinyunlian.jinfu.acct.enums.converter;

import com.xinyunlian.jinfu.acct.enums.ERecvPayType;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 
 * @author cheng
 *
 */
@Converter
public class ERecvPayTypeEnumConverter implements AttributeConverter<ERecvPayType, String> {

	@Override
    public String convertToDatabaseColumn(ERecvPayType attribute) {
        return attribute.getCode();
    }

    @Override
    public ERecvPayType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ERecvPayType.class, dbData);
    }
	
}
