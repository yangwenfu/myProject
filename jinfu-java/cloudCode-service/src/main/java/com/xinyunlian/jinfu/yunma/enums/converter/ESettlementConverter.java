package com.xinyunlian.jinfu.yunma.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.yunma.enums.ESettlement;

import javax.persistence.AttributeConverter;

/**
 * Created by jll on 2016/5/24.
 */
public class ESettlementConverter implements AttributeConverter<ESettlement, String> {

    @Override
    public String convertToDatabaseColumn(ESettlement settlement) {
        return settlement.getCode();
    }

    @Override
    public ESettlement convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ESettlement.class, dbData);
    }
}
