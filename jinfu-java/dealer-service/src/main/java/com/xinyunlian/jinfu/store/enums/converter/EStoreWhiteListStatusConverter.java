package com.xinyunlian.jinfu.store.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.store.enums.EStoreWhiteListStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2017年06月20日.
 */
public class EStoreWhiteListStatusConverter implements AttributeConverter<EStoreWhiteListStatus, String> {

    @Override
    public String convertToDatabaseColumn(EStoreWhiteListStatus eStoreWhiteListStatus) {
        return eStoreWhiteListStatus.getCode();
    }

    @Override
    public EStoreWhiteListStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EStoreWhiteListStatus.class, dbData);
    }
}
