package com.xinyunlian.jinfu.report.allloan.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.report.allloan.enums.ETransferStat;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 
 * @author cheng
 *
 */
@Converter
public class ETransferStatEnumConverter implements AttributeConverter<ETransferStat, String> {

	@Override
    public String convertToDatabaseColumn(ETransferStat attribute) {
        return attribute.getCode();
    }

    @Override
    public ETransferStat convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ETransferStat.class, dbData);
    }
	
}
