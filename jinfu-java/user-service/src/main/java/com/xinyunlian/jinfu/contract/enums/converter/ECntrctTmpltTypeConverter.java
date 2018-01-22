package com.xinyunlian.jinfu.contract.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.contract.enums.ECntrctTmpltType;

import javax.persistence.AttributeConverter;

/**
 * Created by JL on 2016/9/27.
 */
public class ECntrctTmpltTypeConverter implements AttributeConverter<ECntrctTmpltType, String> {

    @Override
    public String convertToDatabaseColumn(ECntrctTmpltType status) {
        return status.getCode();
    }

    @Override
    public ECntrctTmpltType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ECntrctTmpltType.class, dbData);
    }
}
