package com.xinyunlian.jinfu.thirdparty.nbcb.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.thirdparty.nbcb.enums.ENBCBReceiveStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by bright on 2017/5/15.
 */
public class ENBCBReceiveStatusEnumConverter implements AttributeConverter<ENBCBReceiveStatus, String> {
    @Override
    public String convertToDatabaseColumn(ENBCBReceiveStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public ENBCBReceiveStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ENBCBReceiveStatus.class, dbData);
    }
}
