package com.xinyunlian.jinfu.acct.entity;

import com.xinyunlian.jinfu.acct.enums.ERervStatus;
import com.xinyunlian.jinfu.acct.enums.converter.ERervStatusEnumConverter;
import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "AC_CREDIT_LINE_RSRV_DTL")
@EntityListeners(IdInjectionEntityListener.class)
public class CreditLineRsrvDtlPo extends BaseMaintainablePo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "RSRV_ID")
	private String rsrvId;
	
    @Column(name = "ACCT_NO")
    private String acctNo;
	
	@Column(name = "EFF_DT")
	private String effDate;
	
	@Column(name = "EXP_DT")
	private String expDate;
	
	@Convert(converter = ERervStatusEnumConverter.class)
	@Column(name = "STATUS")
	private ERervStatus status;

	@Column(name = "CREDIT_LINE_RSRV")
	private BigDecimal creditLineRsrv;
	
	@Column(name = "TRX_MEMO")
	private String trxMemo;

	public String getRsrvId() {
		return rsrvId;
	}

	public void setRsrvId(String rsrvId) {
		this.rsrvId = rsrvId;
	}

	public String getAcctNo() {
		return acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public String getEffDate() {
		return effDate;
	}

	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public ERervStatus getStatus() {
		return status;
	}

	public void setStatus(ERervStatus status) {
		this.status = status;
	}

	public BigDecimal getCreditLineRsrv() {
		return creditLineRsrv;
	}

	public void setCreditLineRsrv(BigDecimal creditLineRsrv) {
		this.creditLineRsrv = creditLineRsrv;
	}

	public String getTrxMemo() {
		return trxMemo;
	}

	public void setTrxMemo(String trxMemo) {
		this.trxMemo = trxMemo;
	}
	
}
