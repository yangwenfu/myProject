package com.xinyunlian.jinfu.thirdparty.nbcb.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.thirdparty.nbcb.enums.ENBCBApprStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by bright on 2017/5/15.
 */
public class ENBCBApprStatusEnumConverter implements AttributeConverter<ENBCBApprStatus, String> {
    @Override
    public String convertToDatabaseColumn(ENBCBApprStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public ENBCBApprStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ENBCBApprStatus.class, dbData);
    }
}
