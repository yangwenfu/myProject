package com.xinyunlian.jinfu.promo.service;

import com.xinyunlian.jinfu.promo.dto.PromoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Willwang on 2016/11/29.
 */
@Service
public class LoanPromoServiceImpl implements LoanPromoService{

    @Autowired
    private InnerLoanPromoService innerLoanPromoService;

    @Override
    public PromoDto get(String loanId) {
        return innerLoanPromoService.get(loanId);
    }
}
