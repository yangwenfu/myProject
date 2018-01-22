package com.xinyunlian.jinfu.contract.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.contract.enums.ESignedMark;

import javax.persistence.AttributeConverter;

/**
 * Created by JL on 2016/9/27.
 */
public class ESignedMarkConverter implements AttributeConverter<ESignedMark, String> {

    @Override
    public String convertToDatabaseColumn(ESignedMark status) {
        return status.getCode();
    }

    @Override
    public ESignedMark convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ESignedMark.class, dbData);
    }
}
