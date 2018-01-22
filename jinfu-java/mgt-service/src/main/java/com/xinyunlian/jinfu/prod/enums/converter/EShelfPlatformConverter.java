package com.xinyunlian.jinfu.prod.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.prod.enums.EShelfPlatform;

import javax.persistence.AttributeConverter;

/**
 * Created by dongfangchao on 2016/12/5/0005.
 */
public class EShelfPlatformConverter implements AttributeConverter<EShelfPlatform, String> {

    @Override
    public String convertToDatabaseColumn(EShelfPlatform shelfStatus) {
        return shelfStatus.getCode();
    }

    @Override
    public EShelfPlatform convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EShelfPlatform.class, dbData);
    }

}
