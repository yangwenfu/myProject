package com.xinyunlian.jinfu.user.entity;

import com.xinyunlian.jinfu.common.entity.BasePo;
import com.xinyunlian.jinfu.user.enums.ELabelType;
import com.xinyunlian.jinfu.user.enums.converter.ELabelTypeConverter;

import javax.persistence.*;

/**
 * 用户标签Entity
 * @author jll
 * @version 
 */
@Entity
@Table(name = "USER_LABEL")
public class UserLabelPo extends BasePo {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private Long id;

	@Column(name="USER_ID")
	private String userId;

	@Column(name="LABEL_TYPE")
	@Convert(converter = ELabelTypeConverter.class)
	private ELabelType labelType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public ELabelType getLabelType() {
		return labelType;
	}

	public void setLabelType(ELabelType labelType) {
		this.labelType = labelType;
	}
}