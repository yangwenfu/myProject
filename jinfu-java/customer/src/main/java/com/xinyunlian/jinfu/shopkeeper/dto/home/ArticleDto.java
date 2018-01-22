package com.xinyunlian.jinfu.shopkeeper.dto.home;

import java.io.Serializable;

/**
 * 文章表Entity
 *
 * @author jll
 */

public class ArticleDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long articleId;
    private String title;
    private String articleTypeName;
    private String coverPic;
    private String url;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticleTypeName() {
        return articleTypeName;
    }

    public void setArticleTypeName(String articleTypeName) {
        this.articleTypeName = articleTypeName;
    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}


