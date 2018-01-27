package com.xinyunlian.jinfu.share.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.xinyunlian.jinfu.share.entity.RecommendPo;

/**
 * 推荐DAO接口
 * @author jll
 * @version 
 */
public interface RecommendDao extends JpaRepository<RecommendPo, Long>, JpaSpecificationExecutor<RecommendPo> {

    RecommendPo findByUserId(String userId);
	
}
