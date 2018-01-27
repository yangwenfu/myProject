package com.xinyunlian.jinfu.promo.dao;

import com.xinyunlian.jinfu.promo.enums.ERecordType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.xinyunlian.jinfu.promo.entity.WhiteBlackUserPo;

import java.util.List;

/**
 * 黑白名单DAO接口
 * @author jll
 * @version 
 */
public interface WhiteBlackUserDao extends JpaRepository<WhiteBlackUserPo, Long>, JpaSpecificationExecutor<WhiteBlackUserPo> {
	public List<WhiteBlackUserPo> findByPromoIdAndRecordType(Long promoId, ERecordType recordType);

	void deleteByPromoIdAndRecordType(Long promoId, ERecordType recordType);
}
