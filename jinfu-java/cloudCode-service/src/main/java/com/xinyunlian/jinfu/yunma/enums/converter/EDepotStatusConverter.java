package com.xinyunlian.jinfu.yunma.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.yunma.enums.EDepotStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2017/8/29.
 */
public class EDepotStatusConverter implements AttributeConverter<EDepotStatus, String> {

    @Override
    public String convertToDatabaseColumn(EDepotStatus depotStatus) {
        return depotStatus.getCode();
    }

    @Override
    public EDepotStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EDepotStatus.class, dbData);
    }
}
