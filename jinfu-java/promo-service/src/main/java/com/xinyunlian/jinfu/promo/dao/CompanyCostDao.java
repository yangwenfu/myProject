package com.xinyunlian.jinfu.promo.dao;

import com.xinyunlian.jinfu.promo.entity.CompanyCostPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 主体信息DAO接口
 * @author jll
 * @version 
 */
public interface CompanyCostDao extends JpaRepository<CompanyCostPo, Long>, JpaSpecificationExecutor<CompanyCostPo> {
	public List<CompanyCostPo> findByPromoId(Long promoId);

	void deleteByPromoId(Long promoId);
}
