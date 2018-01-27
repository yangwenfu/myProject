package com.xinyunlian.jinfu.dealer.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.dealer.enums.EDealerAuditStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2017年05月04日.
 */
public class EDealerAuditStatusConverter implements AttributeConverter<EDealerAuditStatus, String> {

    @Override
    public String convertToDatabaseColumn(EDealerAuditStatus eDealerAuditStatus) {
        return eDealerAuditStatus.getCode();
    }

    @Override
    public EDealerAuditStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EDealerAuditStatus.class, dbData);
    }
}
