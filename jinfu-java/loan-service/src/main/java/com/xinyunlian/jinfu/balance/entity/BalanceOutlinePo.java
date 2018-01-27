package com.xinyunlian.jinfu.balance.entity;

import com.xinyunlian.jinfu.balance.enums.EBalanceOutlineStatus;
import com.xinyunlian.jinfu.balance.enums.converter.EBalanceOutlineStatusConverter;
import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author willwang
 */
@Entity
@Table(name="BALANCE_OUTLINE")
public class BalanceOutlinePo extends BaseMaintainablePo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="GENERATE_DATE")
	private Date generateDate;

	@Column(name="REPAY_COUNT")
	private Integer repayCount;

	@Column(name="REPAY_AMT")
	private BigDecimal repayAmt;

	@Convert(converter = EBalanceOutlineStatusConverter.class)
	@Column(name = "BALANCE_STATUS")
	private EBalanceOutlineStatus balanceOutlineStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="BALANCE_DATE")
	private Date balanceDate;

	@Column(name = "BALANCE_USER_ID")
	private String balanceUserId;

	@Column(name = "IS_AUTOED")
	private Boolean autoed;

	public Boolean getAutoed() {
		return autoed;
	}

	public void setAutoed(Boolean autoed) {
		this.autoed = autoed;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getGenerateDate() {
		return generateDate;
	}

	public void setGenerateDate(Date generateDate) {
		this.generateDate = generateDate;
	}

	public Integer getRepayCount() {
		return repayCount;
	}

	public void setRepayCount(Integer repayCount) {
		this.repayCount = repayCount;
	}

	public BigDecimal getRepayAmt() {
		return repayAmt;
	}

	public void setRepayAmt(BigDecimal repayAmt) {
		this.repayAmt = repayAmt;
	}

	public EBalanceOutlineStatus getBalanceOutlineStatus() {
		return balanceOutlineStatus;
	}

	public void setBalanceOutlineStatus(EBalanceOutlineStatus balanceOutlineStatus) {
		this.balanceOutlineStatus = balanceOutlineStatus;
	}

	public Date getBalanceDate() {
		return balanceDate;
	}

	public void setBalanceDate(Date balanceDate) {
		this.balanceDate = balanceDate;
	}

	public String getBalanceUserId() {
		return balanceUserId;
	}

	public void setBalanceUserId(String balanceUserId) {
		this.balanceUserId = balanceUserId;
	}

	@Override
	public String toString() {
		return "BalanceOutlinePo{" +
				"id=" + id +
				", generateDate=" + generateDate +
				", repayCount=" + repayCount +
				", repayAmt=" + repayAmt +
				", balanceOutlineStatus=" + balanceOutlineStatus +
				", balanceDate=" + balanceDate +
				", balanceUserId='" + balanceUserId + '\'' +
				", autoed=" + autoed +
				'}';
	}
}
