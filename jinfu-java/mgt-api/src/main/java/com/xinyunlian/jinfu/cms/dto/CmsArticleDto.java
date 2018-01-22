package com.xinyunlian.jinfu.cms.dto;

import com.xinyunlian.jinfu.cms.enums.EArcPlatform;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 文章表Entity
 *
 * @author jll
 */

public class CmsArticleDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long articleId;
    private Long articleTypeId;
    private String articleTypeName;
    private String articleTypeTree;
    private String title;
    private List<EArcPlatform> arcPlatform = new ArrayList<>();
    private String coverPic;
    private String bigPic;
    private String coverPicFull;
    private String bigPicFull;
    private String content;
    private String url;
    private String filePath;
    private Boolean recommend;
    private Boolean top;
    private String seoTitle;
    private String seoKeyword;
    private String seoDescribe;
    private Integer orders;
    private Date createTs;
    private Integer accessViewsCount;
    private Integer accessUpsCount;

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

    public List<EArcPlatform> getArcPlatform() {
        return arcPlatform;
    }

    public void setArcPlatform(List<EArcPlatform> arcPlatform) {
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

    public String getCoverPicFull() {
        return coverPicFull;
    }

    public void setCoverPicFull(String coverPicFull) {
        this.coverPicFull = coverPicFull;
    }

    public String getBigPicFull() {
        return bigPicFull;
    }

    public void setBigPicFull(String bigPicFull) {
        this.bigPicFull = bigPicFull;
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

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public String getArticleTypeName() {
        return articleTypeName;
    }

    public void setArticleTypeName(String articleTypeName) {
        this.articleTypeName = articleTypeName;
    }

    public Integer getAccessViewsCount() {
        return accessViewsCount;
    }

    public void setAccessViewsCount(Integer accessViewsCount) {
        this.accessViewsCount = accessViewsCount;
    }

    public Integer getAccessUpsCount() {
        return accessUpsCount;
    }

    public void setAccessUpsCount(Integer accessUpsCount) {
        this.accessUpsCount = accessUpsCount;
    }
}


