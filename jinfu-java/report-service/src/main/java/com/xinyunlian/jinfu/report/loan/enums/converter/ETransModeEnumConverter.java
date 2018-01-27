package com.xinyunlian.jinfu.report.loan.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.report.loan.enums.ETransMode;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 
 * @author cheng
 *
 */
@Converter
public class ETransModeEnumConverter implements AttributeConverter<ETransMode, String> {

	@Override
    public String convertToDatabaseColumn(ETransMode attribute) {
        return attribute.getCode();
    }

    @Override
    public ETransMode convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ETransMode.class, dbData);
    }
	
}
