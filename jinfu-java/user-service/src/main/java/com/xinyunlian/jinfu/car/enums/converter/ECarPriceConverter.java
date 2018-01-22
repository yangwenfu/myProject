package com.xinyunlian.jinfu.car.enums.converter;

import com.xinyunlian.jinfu.car.enums.ECarPrice;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;

/**
 * Created by jll on 2016/5/24.
 */
public class ECarPriceConverter implements AttributeConverter<ECarPrice, String> {

    @Override
    public String convertToDatabaseColumn(ECarPrice price) {
        return price.getCode();
    }

    @Override
    public ECarPrice convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ECarPrice.class, dbData);
    }
}
