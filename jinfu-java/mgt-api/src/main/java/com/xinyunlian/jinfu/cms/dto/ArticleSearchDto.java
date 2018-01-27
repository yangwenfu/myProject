package com.xinyunlian.jinfu.cms.dto;

import com.xinyunlian.jinfu.cms.enums.EArcPlatform;
import com.xinyunlian.jinfu.common.dto.PagingDto;

import java.util.Date;

/**
 * 文章表Entity
 *
 * @author jll
 */

public class ArticleSearchDto extends PagingDto<CmsArticleDto> {
    private static final long serialVersionUID = 1L;
    private Long articleId;
    private Long articleTypeId;
    private String articleTypeTree;
    private String title;
    private EArcPlatform arcPlatform;
    private String coverPic;
    private String bigPic;
    private String content;
    private String url;
    private String filePath;
    private Boolean recommend;
    private Boolean top;
    private String seoTitle;
    private String seoKeyword;
    private String seoDescribe;
    private Date createTs;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }

    public Long getArticleTypeId() {
        return articleTypeId;
    }

    public void setArticleTypeId(Long articleTypeId) {
        this.articleTypeId = articleTypeId;
    }

    public String getArticleTypeTree() {
        return articleTypeTree;
    }

    public void setArticleTypeTree(String articleTypeTree) {
        this.articleTypeTree = articleTypeTree;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public EArcPlatform getArcPlatform() {
        return arcPlatform;
    }

    public void setArcPlatform(EArcPlatform arcPlatform) {
        this.arcPlatform = arcPlatform;
    }

    public String getCoverPic() {
        return coverPic;
    }

    public void setCoverPic(String coverPic) {
        this.coverPic = coverPic;
    }

    public String getBigPic() {
        return bigPic;
    }

    public void setBigPic(String bigPic) {
        this.bigPic = bigPic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    public Boolean getTop() {
        return top;
    }

    public void setTop(Boolean top) {
        this.top = top;
    }

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public String getSeoKeyword() {
        return seoKeyword;
    }

    public void setSeoKeyword(String seoKeyword) {
        this.seoKeyword = seoKeyword;
    }

    public String getSeoDescribe() {
        return seoDescribe;
    }

    public void setSeoDescribe(String seoDescribe) {
        this.seoDescribe = seoDescribe;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }
}


