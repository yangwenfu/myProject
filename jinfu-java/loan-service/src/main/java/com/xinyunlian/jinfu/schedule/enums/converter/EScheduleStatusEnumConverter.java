package com.xinyunlian.jinfu.schedule.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.schedule.enums.EScheduleStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author willwang
 */
@Converter
public class EScheduleStatusEnumConverter implements AttributeConverter<EScheduleStatus, String>{

	@Override
    public String convertToDatabaseColumn(EScheduleStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public EScheduleStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EScheduleStatus.class, dbData);
    }
	
}
