package com.xinyunlian.jinfu.spider.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.spider.enums.EDataType;

import javax.persistence.AttributeConverter;

/**
 * Created by cong on 2016/5/24.
 */
public class EDataTypeConverter implements AttributeConverter<EDataType, String> {

    @Override
    public String convertToDatabaseColumn(EDataType estate) {
        return estate.getCode();
    }

    @Override
    public EDataType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EDataType.class, dbData);
    }
}
