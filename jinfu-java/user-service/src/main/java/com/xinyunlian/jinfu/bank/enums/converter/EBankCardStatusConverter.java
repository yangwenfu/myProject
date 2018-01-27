package com.xinyunlian.jinfu.bank.enums.converter;

import com.xinyunlian.jinfu.bank.enums.EBankCardStatus;
import com.xinyunlian.jinfu.common.util.EnumHelper;

import javax.persistence.AttributeConverter;

/**
 * Created by cong on 2016/5/24.
 */
public class EBankCardStatusConverter implements AttributeConverter<EBankCardStatus, String> {

    @Override
    public String convertToDatabaseColumn(EBankCardStatus userStatus) {
        return userStatus.getCode();
    }

    @Override
    public EBankCardStatus convertToEntityAttribute(String dbData) {
        return EnumHelper.translate(EBankCardStatus.class, dbData);
    }
}
