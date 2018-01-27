package com.xinyunlian.jinfu.acct.entity;

import com.xinyunlian.jinfu.acct.enums.EBizType;
import com.xinyunlian.jinfu.acct.enums.ERecvPayType;
import com.xinyunlian.jinfu.acct.enums.ETrxType;
import com.xinyunlian.jinfu.acct.enums.converter.EBizTypeEnumConverter;
import com.xinyunlian.jinfu.acct.enums.converter.ERecvPayTypeEnumConverter;
import com.xinyunlian.jinfu.acct.enums.converter.ETrxTypeEnumConverter;
import com.xinyunlian.jinfu.common.entity.BasePo;
import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "AC_TRX_JNL")
@EntityListeners(IdInjectionEntityListener.class)
public class TrxJnlPo extends BasePo {

	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "JNL_NO")
	private String jnlNo;
	
    @Column(name = "ACCT_NO")
	private String acctNo;
	
    @Column(name = "TRX_DT")
	private String trxDate;
	
	@Convert(converter = ERecvPayTypeEnumConverter.class)
	@Column(name = "RECV_PAY_TYPE")
	private ERecvPayType recvPayType;
	
	@Column(name = "TRX_AMT")
	private BigDecimal trxAmt;

    @Column(name = "TRX_MEMO")
	private String trxMemo;
	
	@Convert(converter = ETrxTypeEnumConverter.class)
	@Column(name = "TRX_TYPE")
	private ETrxType trxType;
	
	@Convert(converter = EBizTypeEnumConverter.class)
	@Column(name = "BIZ_TYPE")
	private EBizType bizType;
	
    @Column(name = "BIZ_ID")
	private String bizId;

	public String getJnlNo() {
		return jnlNo;
	}

	public void setJnlNo(String jnlNo) {
		this.jnlNo = jnlNo;
	}

	public String getAcctNo() {
		return acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public String getTrxDate() {
		return trxDate;
	}

	public void setTrxDate(String trxDate) {
		this.trxDate = trxDate;
	}

	public ERecvPayType getRecvPayType() {
		return recvPayType;
	}

	public void setRecvPayType(ERecvPayType recvPayType) {
		this.recvPayType = recvPayType;
	}

	public BigDecimal getTrxAmt() {
		return trxAmt;
	}

	public void setTrxAmt(BigDecimal trxAmt) {
		this.trxAmt = trxAmt;
	}

	public String getTrxMemo() {
		return trxMemo;
	}

	public void setTrxMemo(String trxMemo) {
		this.trxMemo = trxMemo;
	}

	public ETrxType getTrxType() {
		return trxType;
	}

	public void setTrxType(ETrxType trxType) {
		this.trxType = trxType;
	}

	public EBizType getBizType() {
		return bizType;
	}

	public void setBizType(EBizType bizType) {
		this.bizType = bizType;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}
	
}
