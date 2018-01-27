package com.xinyunlian.jinfu.loan.service;

import com.xinyunlian.jinfu.loan.LoanApplExtDto;

import java.util.List;

/**
 * @author willwang
 */
public interface LoanApplExtService {

    /**
     * 根据申请编号找到对应的用户信息拷贝
     * @param applId
     * @return
     */
    LoanApplExtDto findByApplId(String applId);

    List<LoanApplExtDto> listAll(Integer currentPage);

}
