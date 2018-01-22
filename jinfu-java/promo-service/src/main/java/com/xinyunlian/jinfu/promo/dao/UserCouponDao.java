package com.xinyunlian.jinfu.promo.dao;

import com.xinyunlian.jinfu.promo.entity.UserCouponPo;
import com.xinyunlian.jinfu.promo.enums.EUserCouponStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 个人优惠券DAO接口
 * @author jll
 * @version 
 */
public interface UserCouponDao extends JpaRepository<UserCouponPo, Long>, JpaSpecificationExecutor<UserCouponPo> {

    Long countByUserIdAndPromoId(String userId,Long promoId);

    Long countByUserIdAndStatus(String userId, EUserCouponStatus status);

    List<UserCouponPo> findByUserIdAndStatus(String userId, EUserCouponStatus status);

    List<UserCouponPo> findByUserId(String userId);

    List<UserCouponPo> findByUserIdAndStatusAndProdId(String userId, EUserCouponStatus status,String prodId);
	
}
