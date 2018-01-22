package com.xinyunlian.jinfu.store.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.store.enums.EStoreWhiteListRemark;

import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2017年07月02日.
 */
public class EStoreWhiteListRemarkConverter implements AttributeConverter<EStoreWhiteListRemark, String> {

    @Override
    public String convertToDatabaseColumn(EStoreWhiteListRemark eStoreWhiteListRemark) {
        return eStoreWhiteListRemark.getCode();
    }

    @Override
    public EStoreWhiteListRemark convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EStoreWhiteListRemark.class, dbData);
    }
}
