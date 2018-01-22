package com.xinyunlian.jinfu.trans.enums.converters;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.trans.enums.EInsTransType;

import javax.persistence.AttributeConverter;

/**
 * Created by dongfangchao on 2017/6/12/0012.
 */
public class EInsTransTypeConverter implements AttributeConverter<EInsTransType, String> {

    @Override
    public String convertToDatabaseColumn(EInsTransType transType) {
        return transType.getCode();
    }

    @Override
    public EInsTransType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EInsTransType.class, dbData);
    }

}
