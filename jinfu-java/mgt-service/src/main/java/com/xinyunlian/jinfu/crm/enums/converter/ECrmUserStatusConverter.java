package com.xinyunlian.jinfu.crm.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.crm.enums.ECrmUserStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by cong on 2016/5/24.
 */
public class ECrmUserStatusConverter implements AttributeConverter<ECrmUserStatus, String> {

    @Override
    public String convertToDatabaseColumn(ECrmUserStatus userStatus) {
        return userStatus.getCode();
    }

    @Override
    public ECrmUserStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ECrmUserStatus.class, dbData);
    }
}
