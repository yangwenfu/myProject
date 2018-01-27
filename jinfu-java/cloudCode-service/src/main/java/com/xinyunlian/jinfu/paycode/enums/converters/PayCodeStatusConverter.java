package com.xinyunlian.jinfu.paycode.enums.converters;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.payCode.enums.PayCodeStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by carrot on 2017/8/29.
 */
public class PayCodeStatusConverter implements AttributeConverter<PayCodeStatus, String> {
    @Override
    public String convertToDatabaseColumn(PayCodeStatus status) {
        return status.getCode();
    }

    @Override
    public PayCodeStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(PayCodeStatus.class, dbData);
    }
}
