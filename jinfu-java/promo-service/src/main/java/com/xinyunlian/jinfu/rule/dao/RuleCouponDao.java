package com.xinyunlian.jinfu.rule.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.xinyunlian.jinfu.rule.entity.RuleCouponPo;
import org.springframework.data.jpa.repository.Query;

/**
 * 优惠券DAO接口
 * @author jll
 * @version 
 */
public interface RuleCouponDao extends JpaRepository<RuleCouponPo, Long>, JpaSpecificationExecutor<RuleCouponPo> {

    RuleCouponPo findByPromoId(Long promoId);

    void deleteByPromoId(Long promoId);

    RuleCouponPo findByCouponCode(String couponCode);

    @Query(nativeQuery = true, value ="select a.* from rule_coupon a,promo_inf b " +
            "where a.promo_id=b.promo_id and b.status='active' and a.coupon_code=?1 ")
    RuleCouponPo findActiveByCouponCode(String couponCode);
	
}
