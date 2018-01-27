package com.xinyunlian.jinfu.cms.service;

import com.xinyunlian.jinfu.cms.dto.ArticleAccessDto;
import com.xinyunlian.jinfu.cms.dto.ArticleSearchDto;
import com.xinyunlian.jinfu.cms.dto.CmsArticleDto;

import java.util.List;

/**
 * 文章表Service
 *
 * @author jll
 */

public interface CmsArticleService {

    CmsArticleDto save(CmsArticleDto cmsArticleDto);

    void update(List<CmsArticleDto> cmsArticleDtos);

    void delete(Long id);

    CmsArticleDto get(Long id);

    ArticleSearchDto getPage(ArticleSearchDto searchDto);

    Long countByArticleTypeId(Long articleTypeId);

    ArticleAccessDto getArticleAccess(Long id);

    ArticleAccessDto upArticle(Long id);

    void viewArticle(Long id);

}
