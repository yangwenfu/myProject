package com.xinyunlian.jinfu.spider.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.spider.enums.EHttpMethod;
import com.xinyunlian.jinfu.spider.enums.EProcessor;

import javax.persistence.AttributeConverter;

/**
 * Created by cong on 2016/5/24.
 */
public class EProcessorConverter implements AttributeConverter<EProcessor, String> {

    @Override
    public String convertToDatabaseColumn(EProcessor processType) {
        return processType.getCode();
    }

    @Override
    public EProcessor convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EProcessor.class, dbData);
    }
}
