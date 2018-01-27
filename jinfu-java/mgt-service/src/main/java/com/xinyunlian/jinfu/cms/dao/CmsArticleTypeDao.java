package com.xinyunlian.jinfu.cms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.xinyunlian.jinfu.cms.entity.CmsArticleTypePo;

/**
 * 文章类别DAO接口
 * @author jll
 * @version 
 */
public interface CmsArticleTypeDao extends JpaRepository<CmsArticleTypePo, Long>, JpaSpecificationExecutor<CmsArticleTypePo> {
	
}
