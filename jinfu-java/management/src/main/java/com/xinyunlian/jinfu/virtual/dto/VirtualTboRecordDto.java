package com.xinyunlian.jinfu.virtual.dto;

import java.io.Serializable;

/**
 * 虚拟烟草证Entity
 * @author jll
 * @version 
 */

public class VirtualTboRecordDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String tobaccoCertificateNo;

	public String getTobaccoCertificateNo() {
		return tobaccoCertificateNo;
	}

	public void setTobaccoCertificateNo(String tobaccoCertificateNo) {
		this.tobaccoCertificateNo = tobaccoCertificateNo;
	}
}


