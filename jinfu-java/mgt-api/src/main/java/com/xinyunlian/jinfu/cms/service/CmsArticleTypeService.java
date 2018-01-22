package com.xinyunlian.jinfu.cms.service;

import com.xinyunlian.jinfu.cms.dto.CmsArticleTypeDto;

import java.util.List;

/**
 * 文章类别Service
 *
 * @author jll
 */

public interface CmsArticleTypeService {
    CmsArticleTypeDto get(Long id);

    void save(CmsArticleTypeDto cmsArticleTypeDto);

    void delete(Long id);

    void update(List<CmsArticleTypeDto> articleTypeDtos);

    List<CmsArticleTypeDto> getList(CmsArticleTypeDto articleTypeDto);


}
