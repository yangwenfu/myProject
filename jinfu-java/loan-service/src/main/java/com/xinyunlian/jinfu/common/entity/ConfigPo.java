package com.xinyunlian.jinfu.common.entity;

import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;

import javax.persistence.*;

/**
 * 
 * @author willwang
 *
 */
@Entity
@Table(name="GL_CFG")
@EntityListeners(IdInjectionEntityListener.class)
public class ConfigPo extends BaseMaintainablePo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name="CFG_KEY")
	private String cfgKey;
	
    @Column(name="CFG_VALUE")
	private String cfgValue;
	
    @Column(name="MEMO")
	private String memo;

	public String getCfgKey() {
		return cfgKey;
	}

	public void setCfgKey(String cfgKey) {
		this.cfgKey = cfgKey;
	}

	public String getCfgValue() {
		return cfgValue;
	}

	public void setCfgValue(String cfgValue) {
		this.cfgValue = cfgValue;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
