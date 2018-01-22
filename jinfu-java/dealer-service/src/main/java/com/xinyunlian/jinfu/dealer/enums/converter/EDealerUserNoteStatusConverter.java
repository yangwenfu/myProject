package com.xinyunlian.jinfu.dealer.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.dealer.enums.EDealerUserNoteStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2016年08月26日.
 */
public class EDealerUserNoteStatusConverter implements AttributeConverter<EDealerUserNoteStatus, String> {

    @Override
    public String convertToDatabaseColumn(EDealerUserNoteStatus eDealerUserNoteStatus) {
        return eDealerUserNoteStatus.getCode();
    }

    @Override
    public EDealerUserNoteStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EDealerUserNoteStatus.class, dbData);
    }
}
