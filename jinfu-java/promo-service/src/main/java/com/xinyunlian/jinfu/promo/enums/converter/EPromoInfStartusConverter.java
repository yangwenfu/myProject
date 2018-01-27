package com.xinyunlian.jinfu.promo.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.promo.enums.EPromoInfStatus;
import com.xinyunlian.jinfu.promo.enums.ERecordType;

import javax.persistence.AttributeConverter;

/**
 * Created by bright on 2016/11/21.
 */
public class EPromoInfStartusConverter implements AttributeConverter<EPromoInfStatus, String>{
    @Override
    public String convertToDatabaseColumn(EPromoInfStatus attribute) {
        return attribute.getCode();
    }

    @Override
    public EPromoInfStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EPromoInfStatus.class, dbData);
    }
}
