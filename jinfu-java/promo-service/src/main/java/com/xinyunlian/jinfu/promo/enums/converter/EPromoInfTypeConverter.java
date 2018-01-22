package com.xinyunlian.jinfu.promo.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.promo.enums.EPromoInfStatus;
import com.xinyunlian.jinfu.promo.enums.EPromoInfType;

import javax.persistence.AttributeConverter;

/**
 * Created by bright on 2016/11/21.
 */
public class EPromoInfTypeConverter implements AttributeConverter<EPromoInfType, String>{
    @Override
    public String convertToDatabaseColumn(EPromoInfType attribute) {
        return attribute.getCode();
    }

    @Override
    public EPromoInfType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EPromoInfType.class, dbData);
    }
}
