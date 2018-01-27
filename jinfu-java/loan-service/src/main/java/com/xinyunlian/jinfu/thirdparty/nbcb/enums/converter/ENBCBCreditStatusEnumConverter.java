package com.xinyunlian.jinfu.thirdparty.nbcb.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.thirdparty.nbcb.enums.ENBCBCreditStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by bright on 2017/5/15.
 */
public class ENBCBCreditStatusEnumConverter implements AttributeConverter<ENBCBCreditStatus, String> {
    @Override
    public String convertToDatabaseColumn(ENBCBCreditStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public ENBCBCreditStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ENBCBCreditStatus.class, dbData);
    }
}
