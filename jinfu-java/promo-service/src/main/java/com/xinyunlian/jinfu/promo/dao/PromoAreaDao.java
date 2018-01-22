package com.xinyunlian.jinfu.promo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.xinyunlian.jinfu.promo.entity.PromoAreaPo;

import java.util.List;

/**
 * 促销地址DAO接口
 * @author jll
 * @version 
 */
public interface PromoAreaDao extends JpaRepository<PromoAreaPo, Long>, JpaSpecificationExecutor<PromoAreaPo> {
	public List<PromoAreaPo> getByPromoId(Long promoId);

	public void deleteByPromoId(Long promoId);
}
