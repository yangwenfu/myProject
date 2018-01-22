package com.xinyunlian.jinfu.external.service;

import com.xinyunlian.jinfu.external.dto.LoanApplOutBankCardDto;
import com.xinyunlian.jinfu.external.dto.LoanApplOutDto;
import com.xinyunlian.jinfu.loan.enums.EApplOutType;

import java.util.List;
import java.util.Set;

public interface LoanApplOutBankCardService {

    void save(String idCardNo, String bankCardNo);

    void save(LoanApplOutBankCardDto dto);

    LoanApplOutBankCardDto findByApplId(String applId);

    LoanApplOutBankCardDto findByUserLatest(String userId);

    List<LoanApplOutBankCardDto> findByApplIdIn(Set<String> applIds);
}