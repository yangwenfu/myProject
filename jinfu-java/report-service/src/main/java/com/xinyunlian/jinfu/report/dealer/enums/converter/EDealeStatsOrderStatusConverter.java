package com.xinyunlian.jinfu.report.dealer.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.report.dealer.enums.EDealerStatsOrderStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by menglei on 2016年08月26日.
 */
public class EDealeStatsOrderStatusConverter implements AttributeConverter<EDealerStatsOrderStatus, String> {

    @Override
    public String convertToDatabaseColumn(EDealerStatsOrderStatus eDealerStatsOrderStatus) {
        return eDealerStatsOrderStatus.getCode();
    }

    @Override
    public EDealerStatsOrderStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EDealerStatsOrderStatus.class, dbData);
    }
}
