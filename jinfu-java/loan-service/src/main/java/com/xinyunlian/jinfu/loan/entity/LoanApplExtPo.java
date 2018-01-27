package com.xinyunlian.jinfu.loan.entity;

import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "FP_LOAN_APPL_EXT")
@EntityListeners(IdInjectionEntityListener.class)
public class LoanApplExtPo{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "APPL_ID")
	private String applId;

	@Column(name = "USER_EXTRA")
	private String userExtra;

	public String getUserExtra() {
		return userExtra;
	}

	public void setUserExtra(String userExtra) {
		this.userExtra = userExtra;
	}

	public String getApplId() {
		return applId;
	}

	public void setApplId(String applId) {
		this.applId = applId;
	}
}
