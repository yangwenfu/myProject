package com.xinyunlian.jinfu.user.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.user.enums.EMgtUserStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by DongFC on 2016-08-24.
 */
public class EMgtUserStatusConverter implements AttributeConverter<EMgtUserStatus, String>{

    @Override
    public String convertToDatabaseColumn(EMgtUserStatus eMgtUserStatus) {
        return eMgtUserStatus.getCode();
    }

    @Override
    public EMgtUserStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EMgtUserStatus.class, dbData);
    }
}
