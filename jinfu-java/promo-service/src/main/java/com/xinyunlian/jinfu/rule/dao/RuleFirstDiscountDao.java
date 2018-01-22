package com.xinyunlian.jinfu.rule.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.xinyunlian.jinfu.rule.entity.RuleFirstDiscountPo;

/**
 * 首单折扣DAO接口
 * @author jll
 * @version 
 */
public interface RuleFirstDiscountDao extends JpaRepository<RuleFirstDiscountPo, Long>, JpaSpecificationExecutor<RuleFirstDiscountPo> {

    RuleFirstDiscountPo findByPromoId(Long promoId);

    void deleteByPromoId(Long promoId);
	
}
