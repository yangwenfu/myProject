package com.xinyunlian.jinfu.ad.enums.converter;

import com.xinyunlian.jinfu.ad.enums.EPosPlatform;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;

/**
 * Created by dongfangchao on 2016/12/21/0021.
 */
public class EPosPlatformConverter implements AttributeConverter<EPosPlatform, String> {

    @Override
    public String convertToDatabaseColumn(EPosPlatform posPlatform) {
        return posPlatform.getCode();
    }

    @Override
    public EPosPlatform convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EPosPlatform.class, dbData);
    }

}
