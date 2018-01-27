package com.xinyunlian.jinfu.cms.enums.converter;

import com.xinyunlian.jinfu.cms.enums.ENoticePlatform;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;

/**
 * Created by dongfangchao on 2016/12/5/0005.
 */
public class ENoticePlatformConverter implements AttributeConverter<ENoticePlatform, String> {

    @Override
    public String convertToDatabaseColumn(ENoticePlatform noticePlatform) {
        return noticePlatform.getCode();
    }

    @Override
    public ENoticePlatform convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ENoticePlatform.class, dbData);
    }

}
