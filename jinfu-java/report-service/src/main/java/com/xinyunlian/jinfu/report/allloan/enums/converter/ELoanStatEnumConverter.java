package com.xinyunlian.jinfu.report.allloan.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.report.allloan.enums.ELoanStat;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 
 * @author cheng
 *
 */
@Converter
public class ELoanStatEnumConverter implements AttributeConverter<ELoanStat, String> {

	@Override
    public String convertToDatabaseColumn(ELoanStat attribute) {
        return attribute.getCode();
    }

    @Override
    public ELoanStat convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ELoanStat.class, dbData);
    }
	
}
