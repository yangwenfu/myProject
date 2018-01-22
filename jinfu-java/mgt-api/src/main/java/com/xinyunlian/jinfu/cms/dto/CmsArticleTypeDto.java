package com.xinyunlian.jinfu.cms.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章类别Entity
 * @author jll
 * @version 
 */

public class CmsArticleTypeDto implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long articleTypeId;
	private String code;
	private String name;
	private Integer parent;
	private String path;
	private Integer orders;
	private Boolean display;
	private String seoTitle;
	private String seoKeyword;
	private String seoDescribe;
	private String pic;
	private Date createTs;

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

	public Date getCreateTs() {
		return createTs;
	}

	public void setCreateTs(Date createTs) {
		this.createTs = createTs;
	}
}


