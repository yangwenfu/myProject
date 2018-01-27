package com.xinyunlian.jinfu.cms.enums.converter;

import com.xinyunlian.jinfu.cms.enums.ENoticePosition;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;

/**
 * Created by dongfangchao on 2017/5/18/0018.
 */
public class ENoticePositionConverter implements AttributeConverter<ENoticePosition, String> {

    @Override
    public String convertToDatabaseColumn(ENoticePosition noticePosition) {
        return noticePosition.getCode();
    }

    @Override
    public ENoticePosition convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ENoticePosition.class, dbData);
    }

}
