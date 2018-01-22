package com.xinyunlian.jinfu.promo.service;

import com.xinyunlian.jinfu.promo.dto.PromoDto;

/**
 * Created by Willwang on 2016/11/29.
 */
public interface InnerLoanPromoService {

    /**
     * 根据贷款编号获得参加的活动
     * @param loanId
     * @return
     */
    PromoDto get(String loanId);
}
