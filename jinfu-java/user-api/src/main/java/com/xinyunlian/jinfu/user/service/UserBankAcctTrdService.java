package com.xinyunlian.jinfu.user.service;

import com.xinyunlian.jinfu.user.dto.UserBankAcctTrdDto;

import java.util.List;

/**
 * 银行流水Service
 * @author jll
 * @version 
 */

public interface UserBankAcctTrdService {
    UserBankAcctTrdDto save(UserBankAcctTrdDto userBankAcctTrdDto);

    void delete(Long id);

    UserBankAcctTrdDto get(Long id);

    List<UserBankAcctTrdDto> list(Long bankAccountId);

}
