package com.xinyunlian.jinfu.contract.enums.converter;

import com.xinyunlian.jinfu.common.util.EnumHelper;
import com.xinyunlian.jinfu.contract.enums.EBsSignType;

import javax.persistence.AttributeConverter;

/**
 * Created by dongfangchao on 2017/2/14/0014.
 */
public class EBsSignTypeConverter implements AttributeConverter<EBsSignType, String> {

    @Override
    public String convertToDatabaseColumn(EBsSignType signType) {
        return signType.getCode();
    }

    @Override
    public EBsSignType convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EBsSignType.class, dbData);
    }

}
