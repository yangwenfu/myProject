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
@Table(name="BALANCE_CASHIER")
public class BalanceCashierPo extends BaseMaintainablePo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
	private Long id;

	@Column(name = "OUTLINE_ID")
	private Long outlineId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="PAY_DATE")
	private Date payDate;

	@Column(name="BIZ_ID")
	private String bizId;

	@Column(name="PAY_AMT")
	private BigDecimal payAmt;

	@Column(name = "PAY_STATUS")
	private String payStatus;

	@Column(name = "CHANNEL_NAME")
	private String channelName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "BALANCE_DATE")
	private Date balanceDate;

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

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public BigDecimal getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
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

	public EBalanceStatus getBalanceStatus() {
		return balanceStatus;
	}

	public void setBalanceStatus(EBalanceStatus balanceStatus) {
		this.balanceStatus = balanceStatus;
	}

	@Override
	public String toString() {
		return "BalanceCashierPo{" +
				"id=" + id +
				", payDate=" + payDate +
				", bizId='" + bizId + '\'' +
				", payAmt=" + payAmt +
				", payStatus='" + payStatus + '\'' +
				", channelName='" + channelName + '\'' +
				", balanceDate=" + balanceDate +
				", balanceStatus=" + balanceStatus.getText() +
				'}';
	}
}
