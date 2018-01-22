package com.xinyunlian.jinfu.promo.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.promo.enmus.EPromoType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class EPromoTypeConverter implements AttributeConverter<EPromoType, String> {

    @Override
    public String convertToDatabaseColumn(EPromoType promoType) {
        return promoType.getCode();
    }

    @Override
    public EPromoType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EPromoType.class, dbData);
    }
}