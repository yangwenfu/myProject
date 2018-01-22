package com.xinyunlian.jinfu.thirdparty.nbcb.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.thirdparty.nbcb.enums.ENBCBLoanStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by bright on 2017/5/15.
 */
public class ENBCBLoanStatusEnumConverter implements AttributeConverter<ENBCBLoanStatus, String> {
    @Override
    public String convertToDatabaseColumn(ENBCBLoanStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public ENBCBLoanStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ENBCBLoanStatus.class, dbData);
    }
}
