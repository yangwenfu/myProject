package com.xinyunlian.jinfu.report.dealer.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.report.dealer.enums.EInsuranceStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by bright on 2016/11/29.
 */
public class EInsuranceStatusEnumConverter implements AttributeConverter<EInsuranceStatus, String>{
    @Override
    public String convertToDatabaseColumn(EInsuranceStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public EInsuranceStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EInsuranceStatus.class, dbData);
    }
}
