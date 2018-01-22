package com.xinyunlian.jinfu.coupon.service;

import com.xinyunlian.jinfu.common.exception.BizServiceException;
import com.xinyunlian.jinfu.coupon.dto.LoanCouponDto;
import com.xinyunlian.jinfu.coupon.dto.LoanCouponRepayDto;

import java.util.Collection;
import java.util.List;

/**
 * Created by Willwang on 2017/3/27.
 */
public interface LoanCouponService {

    void save(LoanCouponDto loanCouponDto);

    List<LoanCouponDto> listByRepayId(String repayId);

    List<LoanCouponDto> listByRepayIds(Collection<String> repayIds);

    /**
     * 计算用券之后的金额
     * @param repayId
     * @return
     * @throws BizServiceException
     */
    LoanCouponRepayDto calcPrice(String repayId) throws BizServiceException;

}
