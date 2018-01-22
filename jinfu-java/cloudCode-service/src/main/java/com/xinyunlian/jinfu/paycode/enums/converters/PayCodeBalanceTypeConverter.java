package com.xinyunlian.jinfu.paycode.enums.converters;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.payCode.enums.PayCodeBalanceType;

import javax.persistence.AttributeConverter;

/**
 * Created by carrot on 2017/8/29.
 */
public class PayCodeBalanceTypeConverter implements AttributeConverter<PayCodeBalanceType, String> {
    @Override
    public String convertToDatabaseColumn(PayCodeBalanceType type) {
        return type.getCode();
    }

    @Override
    public PayCodeBalanceType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(PayCodeBalanceType.class, dbData);
    }
}
