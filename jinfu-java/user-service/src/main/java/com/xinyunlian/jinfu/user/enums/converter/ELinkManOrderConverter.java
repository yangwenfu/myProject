package com.xinyunlian.jinfu.user.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.user.enums.ELinkManOrder;

import javax.persistence.AttributeConverter;

/**
 * Created by cong on 2016/5/24.
 */
public class ELinkManOrderConverter implements AttributeConverter<ELinkManOrder, String> {

    @Override
    public String convertToDatabaseColumn(ELinkManOrder arg) {
        return arg.getCode();
    }

    @Override
    public ELinkManOrder convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ELinkManOrder.class, dbData);
    }
}
