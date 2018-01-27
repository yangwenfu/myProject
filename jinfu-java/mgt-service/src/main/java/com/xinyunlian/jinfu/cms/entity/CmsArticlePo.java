package com.xinyunlian.jinfu.cms.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * 文章表Entity
 * @author jll
 * @version 
 */
@Entity
@Table(name = "CMS_ARTICLE")
public class CmsArticlePo extends BaseMaintainablePo {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ARTICLE_ID")
	private Long articleId;

	@Column(name="ARTICLE_TYPE_ID")
	private Long articleTypeId;

	@Column(name="ARTICLE_TYPE_TREE")
	private String articleTypeTree;

	@Column(name="TITLE")
	private String title;

	@Column(name="ARC_PLATFORM")
	private String arcPlatform;

	@Column(name="COVER_PIC")
	private String coverPic;

	@Column(name="BIG_PIC")
	private String bigPic;

	@Column(name="CONTENT")
	private String content;

	@Column(name="URL")
	private String url;

	@Column(name="FILE_PATH")
	private String filePath;

	@Column(name="RECOMMEND")
	private Boolean recommend;

	@Column(name="TOP")
	private Boolean top;

	@Column(name="SEO_TITLE")
	private String seoTitle;

	@Column(name="SEO_KEYWORD")
	private String seoKeyword;

	@Column(name="SEO_DESCRIBE")
	private String seoDescribe;

	@Column(name="ORDERS")
	private Integer orders;

	@Column(name="VIEWS_COUNT")
	private Integer viewsCount;

	@Column(name="UPS_COUNT")
	private Integer upsCount;

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

	public String getArcPlatform() {
		return arcPlatform;
	}

	public void setArcPlatform(String arcPlatform) {
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

	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
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


