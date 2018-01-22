package com.xinyunlian.jinfu.external.service;

import com.xinyunlian.jinfu.external.dto.LoanOutRiskDto;

/**
 * Created by godslhand on 2017/7/3.
 */
public interface LoanOutRiskService {

    LoanOutRiskDto findByUserIdAndApplyId(String userId,String applyId);

    LoanOutRiskDto save(LoanOutRiskDto loanOutRiskDto);
}
