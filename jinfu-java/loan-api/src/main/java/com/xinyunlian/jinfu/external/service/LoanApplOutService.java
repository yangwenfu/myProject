package com.xinyunlian.jinfu.external.service;

import com.xinyunlian.jinfu.external.dto.LoanApplOutDto;
import com.xinyunlian.jinfu.loan.enums.EApplOutType;

import java.util.List;

public interface LoanApplOutService {

    void save(LoanApplOutDto loanApplOutDto);

    List<LoanApplOutDto> findByApplId(String applId);

    LoanApplOutDto findByApplIdAndType(String applId, EApplOutType type);

    LoanApplOutDto findByOutTradeNoAndType(String outTradeNo, EApplOutType type);

}