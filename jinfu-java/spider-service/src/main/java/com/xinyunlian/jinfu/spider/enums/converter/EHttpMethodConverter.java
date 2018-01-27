package com.xinyunlian.jinfu.spider.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.spider.enums.EDataType;
import com.xinyunlian.jinfu.spider.enums.EHttpMethod;

import javax.persistence.AttributeConverter;

/**
 * Created by cong on 2016/5/24.
 */
public class EHttpMethodConverter implements AttributeConverter<EHttpMethod, String> {

    @Override
    public String convertToDatabaseColumn(EHttpMethod method) {
        return method.getCode();
    }

    @Override
    public EHttpMethod convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EHttpMethod.class, dbData);
    }
}
