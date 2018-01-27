package com.xinyunlian.jinfu.user.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.user.enums.EDealerUserStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2016年08月26日.
 */
public class EDealerUserStatusConverter implements AttributeConverter<EDealerUserStatus, String> {

    @Override
    public String convertToDatabaseColumn(EDealerUserStatus eDealerUserStatus) {
        return eDealerUserStatus.getCode();
    }

    @Override
    public EDealerUserStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EDealerUserStatus.class, dbData);
    }
}
