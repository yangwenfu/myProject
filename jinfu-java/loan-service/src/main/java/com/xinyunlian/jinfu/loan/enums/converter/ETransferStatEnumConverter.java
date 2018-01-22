package com.xinyunlian.jinfu.loan.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.loan.enums.ELoanStat;
import com.xinyunlian.jinfu.loan.enums.ETransferStat;

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
