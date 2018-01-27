package com.xinyunlian.jinfu.user.service;

import com.xinyunlian.jinfu.user.dto.UserBankAcctDto;

import java.util.List;

/**
 * 银行流水账户Service
 * @author jll
 * @version 
 */

public interface UserBankAcctService {
    UserBankAcctDto save(UserBankAcctDto userBankAcctDto);

    void delete(Long id);

    UserBankAcctDto get(Long id);

    List<UserBankAcctDto> list(String userId);

}
