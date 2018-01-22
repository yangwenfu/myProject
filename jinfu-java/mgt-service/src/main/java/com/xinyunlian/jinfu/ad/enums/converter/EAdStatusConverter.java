package com.xinyunlian.jinfu.ad.enums.converter;

import com.xinyunlian.jinfu.ad.enums.EAdStatus;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;

/**
 * Created by dongfangchao on 2016/12/21/0021.
 */
public class EAdStatusConverter implements AttributeConverter<EAdStatus, String> {

    @Override
    public String convertToDatabaseColumn(EAdStatus adStatus) {
        return adStatus.getCode();
    }

    @Override
    public EAdStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EAdStatus.class, dbData);
    }

}
