package com.xinyunlian.jinfu.loan.service;

import com.xinyunlian.jinfu.loan.dto.req.LoanApplySearchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoanApplQueryServiceImpl implements LoanApplQueryService {
    @Autowired
    private InnerLoanApplQueryService innerLoanApplQueryService;

    @Override
    public LoanApplySearchDto listLoanAppl(LoanApplySearchDto search) {
        return innerLoanApplQueryService.listLoanAppl(search);
    }

}
