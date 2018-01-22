package com.xinyunlian.jinfu.loan.service;

import com.xinyunlian.jinfu.loan.dto.LoanApplUserDto;

import java.util.List;

/**
 * @author willwang
 */
public interface LoanApplUserService {

    /**
     * 根据申请编号找到对应的用户信息拷贝
     * @param applId
     * @return
     */
    LoanApplUserDto findByApplId(String applId);

    void save(LoanApplUserDto dto);

}
