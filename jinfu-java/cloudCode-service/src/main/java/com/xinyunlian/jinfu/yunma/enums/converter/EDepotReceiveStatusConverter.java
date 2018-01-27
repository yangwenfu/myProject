package com.xinyunlian.jinfu.yunma.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.yunma.enums.EDepotReceiveStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2017/8/29.
 */
public class EDepotReceiveStatusConverter implements AttributeConverter<EDepotReceiveStatus, String> {

    @Override
    public String convertToDatabaseColumn(EDepotReceiveStatus depotReceiveStatus) {
        return depotReceiveStatus.getCode();
    }

    @Override
    public EDepotReceiveStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EDepotReceiveStatus.class, dbData);
    }
}
