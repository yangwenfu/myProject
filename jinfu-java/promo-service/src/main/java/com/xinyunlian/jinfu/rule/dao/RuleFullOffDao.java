package com.xinyunlian.jinfu.rule.dao;

import com.xinyunlian.jinfu.rule.entity.RuleFullOffPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 满减DAO接口
 * @author jll
 * @version 
 */
public interface RuleFullOffDao extends JpaRepository<RuleFullOffPo, Long>, JpaSpecificationExecutor<RuleFullOffPo> {

    RuleFullOffPo findByPromoId(Long promoId);
	
}
