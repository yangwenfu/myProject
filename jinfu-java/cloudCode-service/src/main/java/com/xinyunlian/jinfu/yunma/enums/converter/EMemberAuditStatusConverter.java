package com.xinyunlian.jinfu.yunma.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.yunma.enums.EMemberAuditStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by jll on 2016/5/24.
 */
public class EMemberAuditStatusConverter implements AttributeConverter<EMemberAuditStatus, String> {

    @Override
    public String convertToDatabaseColumn(EMemberAuditStatus status) {
        return status.getCode();
    }

    @Override
    public EMemberAuditStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EMemberAuditStatus.class, dbData);
    }
}
