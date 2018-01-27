package com.xinyunlian.jinfu.report.loan.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.report.loan.enums.ERepayMode;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 
 * @author cheng
 *
 */
@Converter
public class ERepayModeEnumConverter implements AttributeConverter<ERepayMode, String> {

	@Override
    public String convertToDatabaseColumn(ERepayMode attribute) {
        return attribute.getCode();
    }

    @Override
    public ERepayMode convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ERepayMode.class, dbData);
    }
	
}
