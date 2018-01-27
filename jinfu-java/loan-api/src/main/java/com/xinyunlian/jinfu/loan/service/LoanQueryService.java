package com.xinyunlian.jinfu.loan.service;

import com.xinyunlian.jinfu.loan.dto.resp.MLoanDetailDto;
import com.xinyunlian.jinfu.loan.dto.req.LoanSearchListDto;

/**
 * Created by JL on 2016/9/28.
 */
public interface LoanQueryService {

    LoanSearchListDto loanList(LoanSearchListDto loanSearchListDto);

    MLoanDetailDto loanDetail(String loanId);

}
