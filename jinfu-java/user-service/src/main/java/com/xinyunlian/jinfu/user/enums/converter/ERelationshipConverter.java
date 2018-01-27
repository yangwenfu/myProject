package com.xinyunlian.jinfu.user.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.user.enums.ERelationship;

import javax.persistence.AttributeConverter;

/**
 * Created by cong on 2016/5/24.
 */
public class ERelationshipConverter implements AttributeConverter<ERelationship, String> {

    @Override
    public String convertToDatabaseColumn(ERelationship arg) {
        return arg.getCode();
    }

    @Override
    public ERelationship convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ERelationship.class, dbData);
    }
}
