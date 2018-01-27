package com.xinyunlian.jinfu.loan.service;

import com.xinyunlian.jinfu.calc.dto.LoanCalcDto;
import com.xinyunlian.jinfu.common.util.AmtUtils;
import com.xinyunlian.jinfu.coupon.dto.LoanCouponCalcDto;
import com.xinyunlian.jinfu.promo.dto.AllCouponDto;
import com.xinyunlian.jinfu.promo.dto.UserCouponDto;
import com.xinyunlian.jinfu.repay.dto.req.RepayReqDto;
import com.xinyunlian.jinfu.repay.service.RepayService;
import com.xinyunlian.jinfu.rule.enums.ECouponType;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by Willwang on 2017/3/27.
 */
@Service
public class PrivateCalcService {

    @Autowired
    private RepayService repayService;

    @Autowired
    private PrivatePromoService privatePromoService;

    /**
     * 统一还款计算方法
     *
     * @param request
     * @return
     */
    public LoanCalcDto calc(String userId, RepayReqDto request) {
        LoanCalcDto loanCalcDto = repayService.calc(userId, request);

        //保存计算后总额
        loanCalcDto.setTotal(
                loanCalcDto.getCapital()
                        .add(loanCalcDto.getInterest())
                        .add(loanCalcDto.getFine())
                        .add(loanCalcDto.getFee())
        );

        LoanCouponCalcDto loanCouponDto = new LoanCouponCalcDto();
        loanCouponDto.setAmt(null);
        loanCouponDto.setCount(0L);

        loanCalcDto.setCoupon(loanCouponDto);

        Long couponId = CollectionUtils.isEmpty(request.getCouponIds()) ? 0L : request.getCouponIds().get(0);
        if (couponId.equals(0L)) {
            AllCouponDto allCouponDto = privatePromoService.listLoanCoupons(userId, request.getLoanId());
            loanCalcDto.getCoupon().setCount(allCouponDto.getUsedCount());
            return loanCalcDto;
        }

        UserCouponDto userCouponDto = privatePromoService.getUserCoupon(userId, request.getLoanId(), couponId);

        //拿不到优惠券或者优惠券不是属于免息券类型
        if (userCouponDto == null || !ECouponType.INTEREST.equals(userCouponDto.getCouponType())) {
            return loanCalcDto;
        }

        //计算优惠券扣减了多少额度
        BigDecimal price = AmtUtils.min(loanCalcDto.getInterest(), userCouponDto.getPrice());
        loanCalcDto.setPromoTotal(AmtUtils.positive(loanCalcDto.getTotal().subtract(price)));

        loanCalcDto.getCoupon().setAmt(price);
        loanCalcDto.getCoupon().setCouponId(userCouponDto.getId());

        return loanCalcDto;
    }

}
