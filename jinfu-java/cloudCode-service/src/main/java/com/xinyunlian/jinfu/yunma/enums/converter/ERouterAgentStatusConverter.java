package com.xinyunlian.jinfu.yunma.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.yunma.enums.ERouterAgentStatus;

import javax.persistence.AttributeConverter;

/**
 * Created by jll on 2016/5/24.
 */
public class ERouterAgentStatusConverter implements AttributeConverter<ERouterAgentStatus, String> {

    @Override
    public String convertToDatabaseColumn(ERouterAgentStatus status) {
        return status.getCode();
    }

    @Override
    public ERouterAgentStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(ERouterAgentStatus.class, dbData);
    }
}
