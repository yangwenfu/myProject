package com.xinyunlian.jinfu.balance.entity;

import com.xinyunlian.jinfu.balance.enums.EBalanceStatus;
import com.xinyunlian.jinfu.balance.enums.converter.EBalanceStatusConverter;
import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.loan.enums.ERepayStatus;
import com.xinyunlian.jinfu.loan.enums.converter.ERepayStatusEnumConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author willwang
 */
@Entity
@Table(name="BALANCE_DETAIL")
public class BalanceDetailPo extends BaseMaintainablePo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
	private Long id;

	@Column(name = "OUTLINE_ID")
	private Long outlineId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="REPAY_DATE")
	private Date repayDate;

	@Column(name="REPAY_ID")
	private String repayId;

	@Column(name="REPAY_AMT")
	private BigDecimal repayAmt;

	@Column(name = "REPAY_STATUS")
	@Convert(converter = ERepayStatusEnumConverter.class)
	private ERepayStatus repayStatus;

	@Column(name = "CHANNEL_NAME")
	private String channelName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BALANCE_DATE")
	private Date balanceDate;

	@Column(name = "USER_ID")
	private String userId;

	@Column(name = "LOAN_ID")
	private String loanId;

	@Column(name = "PROD_NAME")
	private String prodName;

	@Convert(converter = EBalanceStatusConverter.class)
	@Column(name = "BALANCE_STATUS")
	private EBalanceStatus balanceStatus;

	public Long getOutlineId() {
		return outlineId;
	}

	public void setOutlineId(Long outlineId) {
		this.outlineId = outlineId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getRepayDate() {
		return repayDate;
	}

	public void setRepayDate(Date repayDate) {
		this.repayDate = repayDate;
	}

	public String getRepayId() {
		return repayId;
	}

	public void setRepayId(String repayId) {
		this.repayId = repayId;
	}

	public BigDecimal getRepayAmt() {
		return repayAmt;
	}

	public void setRepayAmt(BigDecimal repayAmt) {
		this.repayAmt = repayAmt;
	}

	public ERepayStatus getRepayStatus() {
		return repayStatus;
	}

	public void setRepayStatus(ERepayStatus repayStatus) {
		this.repayStatus = repayStatus;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public Date getBalanceDate() {
		return balanceDate;
	}

	public void setBalanceDate(Date balanceDate) {
		this.balanceDate = balanceDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public EBalanceStatus getBalanceStatus() {
		return balanceStatus;
	}

	public void setBalanceStatus(EBalanceStatus balanceStatus) {
		this.balanceStatus = balanceStatus;
	}

	@Override
	public String toString() {
		return "BalanceDetailPo{" +
				"id=" + id +
				", outlineId=" + outlineId +
				", repayDate=" + repayDate +
				", repayId='" + repayId + '\'' +
				", repayAmt=" + repayAmt +
				", repayStatus=" + repayStatus +
				", channelName='" + channelName + '\'' +
				", balanceDate=" + balanceDate +
				", userId='" + userId + '\'' +
				", loanId='" + loanId + '\'' +
				", prodName='" + prodName + '\'' +
				", balanceStatus=" + balanceStatus.getText() +
				'}';
	}
}
