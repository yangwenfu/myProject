package com.xinyunlian.jinfu.prod.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.prod.enums.EUserGroup;

import javax.persistence.AttributeConverter;

/**
 * Created by dongfangchao on 2016/12/5/0005.
 */
public class EUserGroupConverter implements AttributeConverter<EUserGroup, String> {

    @Override
    public String convertToDatabaseColumn(EUserGroup userGroup) {
        return userGroup.getCode();
    }

    @Override
    public EUserGroup convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EUserGroup.class, dbData);
    }

}
