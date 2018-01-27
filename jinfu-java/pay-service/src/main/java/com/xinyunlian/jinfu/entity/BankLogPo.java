package com.xinyunlian.jinfu.entity;

import com.xinyunlian.jinfu.common.entity.BasePo;
import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;
import com.xinyunlian.jinfu.converter.EMsgTypeConverter;
import com.xinyunlian.jinfu.converter.ETrxTypeConverter;
import com.xinyunlian.jinfu.enums.EMsgType;
import com.xinyunlian.jinfu.enums.ETrxType;

import javax.persistence.*;


@Entity
@Table(name = "BANK_LOG")
@EntityListeners(IdInjectionEntityListener.class)
public class BankLogPo extends BasePo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "JNL_NO")
	private String jnlNo;

	@Column(name = "TRX_TYPE")
	@Convert(converter = ETrxTypeConverter.class)
	private ETrxType trxType;

	@Column(name = "MSG_TYPE")
	@Convert(converter = EMsgTypeConverter.class)
	private EMsgType msgType;

	@Column(name = "MSG_BODY")
	private String msgBody;

	@Column(name = "RET_CODE")
	private String retCode;

	@Column(name = "RET_MSG")
	private String retMsg;

	public String getJnlNo() {
		return jnlNo;
	}

	public void setJnlNo(String jnlNo) {
		this.jnlNo = jnlNo;
	}

	public EMsgType getMsgType() {
		return msgType;
	}

	public void setMsgType(EMsgType msgType) {
		this.msgType = msgType;
	}

	public ETrxType getTrxType() {
		return trxType;
	}

	public void setTrxType(ETrxType trxType) {
		this.trxType = trxType;
	}

	public String getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

}
