package com.xinyunlian.jinfu.coupon.dao;

import com.xinyunlian.jinfu.coupon.entity.LoanCouponPo;
import com.xinyunlian.jinfu.promo.entity.PromoPo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * @author willwang
 */
public interface LoanCouponDao extends JpaRepository<LoanCouponPo, Long> {

    List<LoanCouponPo> findByRepayId(String repayId);

    List<LoanCouponPo> findByRepayIdIn(Collection<String> repayIds);

}
