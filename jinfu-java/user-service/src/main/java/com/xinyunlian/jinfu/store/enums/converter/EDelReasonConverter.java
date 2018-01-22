package com.xinyunlian.jinfu.store.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.store.enums.EDelReason;

import javax.persistence.AttributeConverter;

/**
 * Created by jll on 2016/5/24.
 */
public class EDelReasonConverter implements AttributeConverter<EDelReason, String> {

    @Override
    public String convertToDatabaseColumn(EDelReason reason) {
        return reason.getCode();
    }

    @Override
    public EDelReason convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EDelReason.class, dbData);
    }
}
