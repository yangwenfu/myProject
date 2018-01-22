package com.xinyunlian.jinfu.acct.enums.converter;

import com.xinyunlian.jinfu.acct.enums.EAcctStatus;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 
 * @author cheng
 *
 */
@Converter
public class EAcctStatusEnumConverter implements AttributeConverter<EAcctStatus, String>{

	@Override
    public String convertToDatabaseColumn(EAcctStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public EAcctStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EAcctStatus.class, dbData);
    }
	
}
