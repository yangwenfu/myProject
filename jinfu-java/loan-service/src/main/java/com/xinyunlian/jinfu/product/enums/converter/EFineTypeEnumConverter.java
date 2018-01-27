package com.xinyunlian.jinfu.product.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.product.enums.EFineType;
import com.xinyunlian.jinfu.product.enums.EViolateType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class EFineTypeEnumConverter implements AttributeConverter<EFineType, String> {

    @Override
    public String convertToDatabaseColumn(EFineType attribute) {
        return attribute.getCode();
    }

    @Override
    public EFineType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EFineType.class, dbData);
    }

}
