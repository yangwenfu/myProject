package com.xinyunlian.jinfu.store.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.store.enums.EStoreStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by cong on 2016/5/24.
 */
public class EStoreStatusConverter implements AttributeConverter<EStoreStatus, String> {

    @Override
    public String convertToDatabaseColumn(EStoreStatus userStatus) {
        return userStatus.getCode();
    }

    @Override
    public EStoreStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EStoreStatus.class, dbData);
    }
}
