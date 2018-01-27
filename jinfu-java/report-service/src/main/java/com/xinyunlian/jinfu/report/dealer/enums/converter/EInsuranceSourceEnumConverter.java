package com.xinyunlian.jinfu.report.dealer.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.report.dealer.enums.EInsuranceSource;

import javax.persistence.AttributeConverter;

/**
 * Created by bright on 2016/11/29.
 */
public class EInsuranceSourceEnumConverter implements AttributeConverter<EInsuranceSource, String>{
    @Override
    public String convertToDatabaseColumn(EInsuranceSource attribute) {
        return attribute.getCode();
    }

    @Override
    public EInsuranceSource convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EInsuranceSource.class, dbData);
    }
}
