package com.xinyunlian.jinfu.rule.dao;

import com.xinyunlian.jinfu.rule.entity.RuleFirstGiftPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 首单有礼DAO接口
 * @author jll
 * @version 
 */
public interface RuleFirstGiftDao extends JpaRepository<RuleFirstGiftPo, Long>, JpaSpecificationExecutor<RuleFirstGiftPo> {

    List<RuleFirstGiftPo> findByPromoId(Long promoId);

    void deleteByPromoId(Long promoId);
	
}
