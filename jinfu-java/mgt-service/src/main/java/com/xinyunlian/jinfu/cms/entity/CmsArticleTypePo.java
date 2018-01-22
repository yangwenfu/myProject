package com.xinyunlian.jinfu.cms.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;

/**
 * 文章类别Entity
 * @author jll
 * @version 
 */
@Entity
@Table(name = "CMS_ARTICLE_TYPE")
public class CmsArticleTypePo extends BaseMaintainablePo {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ARTICLE_TYPE_ID")
	private Long articleTypeId;

	@Column(name="CODE")
	private String code;

	@Column(name="NAME")
	private String name;

	@Column(name="PARENT")
	private Integer parent;

	@Column(name="PATH")
	private String path;

	@Column(name="ORDERS")
	private Integer orders;

	@Column(name="DISPLAY")
	private Boolean display;

	@Column(name="SEO_TITLE")
	private String seoTitle;

	@Column(name="SEO_KEYWORD")
	private String seoKeyword;

	@Column(name="SEO_DESCRIBE")
	private String seoDescribe;

	@Column(name="PIC")
	private String pic;

	public Long getArticleTypeId() {
		return articleTypeId;
	}

	public void setArticleTypeId(Long articleTypeId) {
		this.articleTypeId = articleTypeId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParent() {
		return parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getOrders() {
		return orders;
	}

	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	public Boolean getDisplay() {
		return display;
	}

	public void setDisplay(Boolean display) {
		this.display = display;
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

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}
}


