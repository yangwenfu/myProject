package com.xinyunlian.jinfu.external.service;

import com.xinyunlian.jinfu.external.dto.LoanApplOutUserDto;
import com.xinyunlian.jinfu.loan.enums.EApplOutType;

public interface LoanApplOutUserService {

    LoanApplOutUserDto save(LoanApplOutUserDto loanApplOutUserDto);

    LoanApplOutUserDto findByUserIdAndType(String userId, EApplOutType type);

    LoanApplOutUserDto findByOutUserIdAndType(String outUserId, EApplOutType type);

}