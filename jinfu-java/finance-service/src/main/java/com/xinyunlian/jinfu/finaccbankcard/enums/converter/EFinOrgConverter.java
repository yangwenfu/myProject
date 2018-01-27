package com.xinyunlian.jinfu.finaccbankcard.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.finaccbankcard.enums.EFinOrg;

import javax.persistence.AttributeConverter;

/**
 * Created by DongFC on 2016-08-24.
 */
public class EFinOrgConverter implements AttributeConverter<EFinOrg, String> {

    @Override
    public String convertToDatabaseColumn(EFinOrg finOrg) {
        return finOrg.getCode();
    }

    @Override
    public EFinOrg convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EFinOrg.class, dbData);
    }
}
