package com.xinyunlian.jinfu.cms.dto;

import java.io.Serializable;

/**
 * 文章表Entity
 *
 * @author jll
 */

public class ArticleAccessDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long articleId;

    private Integer viewsCount;

    private Integer upsCount;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Integer getViewsCount() {
        return viewsCount;
    }

    public void setViewsCount(Integer viewsCount) {
        this.viewsCount = viewsCount;
    }

    public Integer getUpsCount() {
        return upsCount;
    }

    public void setUpsCount(Integer upsCount) {
        this.upsCount = upsCount;
    }
}


