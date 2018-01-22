package com.xinyunlian.jinfu.trade.enums.converters;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.trade.enums.EBizCode;

import javax.persistence.AttributeConverter;

/**
 * Created by King on 2016年11月20日.
 */
public class EBizCodeConverter implements AttributeConverter<EBizCode, String> {
    @Override
    public String convertToDatabaseColumn(EBizCode bizCode) {
        return bizCode.getCode();
    }

    @Override
    public EBizCode convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EBizCode.class, dbData);
    }
}
