package com.xinyunlian.jinfu.cms.dao;

import com.xinyunlian.jinfu.cms.entity.CmsArticlePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 文章表DAO接口
 * @author jll
 * @version 
 */
public interface CmsArticleDao extends JpaRepository<CmsArticlePo, Long>, JpaSpecificationExecutor<CmsArticlePo> {
    Long countByArticleTypeId(Long articleTypeId);
}
