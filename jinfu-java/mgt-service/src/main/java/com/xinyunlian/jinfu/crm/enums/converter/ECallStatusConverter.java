package com.xinyunlian.jinfu.crm.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.crm.enums.ECallStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by cong on 2016/5/24.
 */
public class ECallStatusConverter implements AttributeConverter<ECallStatus, String> {

    @Override
    public String convertToDatabaseColumn(ECallStatus userStatus) {
        return userStatus.getCode();
    }

    @Override
    public ECallStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ECallStatus.class, dbData);
    }
}
