package com.xinyunlian.jinfu.external.dto;


import java.io.Serializable;

public class RequsetInfo<T> implements Serializable {
	private String method;
	private String ver;
	private String channelId;
	private T params;
	private String signType;
	private String sign;
	private Integer statusCode;
	private String errMsg;

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}
	
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public T getParams() {
		return params;
	}

	public void setParams(T params) {
		this.params = params;
	}

	@Override
	public String toString() {
		return "RequsetInfo [method=" + method + ", ver=" + ver + ", channelId=" + channelId + ", params=" + params
				+ ", signType=" + signType + ", sign=" + sign + ", statusCode=" + statusCode + ", errMsg=" + errMsg
				+ "]";
	}

}
