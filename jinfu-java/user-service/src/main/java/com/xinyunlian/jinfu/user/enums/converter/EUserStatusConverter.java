package com.xinyunlian.jinfu.user.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.user.enums.EUserStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by cong on 2016/5/24.
 */
public class EUserStatusConverter implements AttributeConverter<EUserStatus, String> {

    @Override
    public String convertToDatabaseColumn(EUserStatus userStatus) {
        return userStatus.getCode();
    }

    @Override
    public EUserStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EUserStatus.class, dbData);
    }
}
