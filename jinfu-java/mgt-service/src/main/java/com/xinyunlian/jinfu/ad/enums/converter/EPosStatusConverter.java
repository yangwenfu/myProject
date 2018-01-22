package com.xinyunlian.jinfu.ad.enums.converter;

import com.xinyunlian.jinfu.ad.enums.EPosStatus;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;

/**
 * Created by dongfangchao on 2016/12/21/0021.
 */
public class EPosStatusConverter implements AttributeConverter<EPosStatus, String> {

    @Override
    public String convertToDatabaseColumn(EPosStatus posStatus) {
        return posStatus.getCode();
    }

    @Override
    public EPosStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EPosStatus.class, dbData);
    }

}
