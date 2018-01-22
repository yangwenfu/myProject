package com.xinyunlian.jinfu.house.entity;

import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.user.enums.EHouseProperty;
import com.xinyunlian.jinfu.user.enums.converter.EHousePropertyConverter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户房产信息Entity
 * @author jll
 * @version 
 */
@Entity
@Table(name = "USER_HOUSE")
public class UserHousePo extends BaseMaintainablePo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private Long id;

	@Column(name="USER_ID")
	private String userId;

	@Column(name="OWNER_NAME")
	private String ownerName;

	@Column(name="SHARE_NAME")
	private String shareName;

	@Column(name="HOUSEMATE")
	private String housemate;

	@Column(name="BUY_DATE")
	private Date buyDate;

	@Column(name="ADDRESS")
	private String address;

	@Column(name="AREA")
	private String area;

	@Column(name="PROPERTY")
	@Convert(converter = EHousePropertyConverter.class)
	private EHouseProperty property;

	@Column(name="LOT")
	private Double lot;

	@Column(name="BUILD_AREA")
	private Double buildArea;

	@Column(name="BUY_UNIT_PRICE")
	private BigDecimal buyUnitPrice;

	@Column(name="AGE")
	private Integer age;

	@Column(name="LOAN_TERM")
	private Integer loanTerm;

	@Column(name="REPAY_MONTH")
	private BigDecimal repayMonth;

	@Column(name="LOAN_BALANCE")
	private BigDecimal loanBalance;

	@Column(name="IS_RENT")
	private Boolean rent;

	@Column(name="RENT_AMOUNT")
	private BigDecimal rentAmount;

	@Column(name="RENT_BEGIN")
	private Date rentBegin;

	@Column(name="RENT_END")
	private Date rentEnd;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getShareName() {
		return shareName;
	}

	public void setShareName(String shareName) {
		this.shareName = shareName;
	}

	public String getHousemate() {
		return housemate;
	}

	public void setHousemate(String housemate) {
		this.housemate = housemate;
	}

	public Date getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public EHouseProperty getProperty() {
		return property;
	}

	public void setProperty(EHouseProperty property) {
		this.property = property;
	}

	public Double getLot() {
		return lot;
	}

	public void setLot(Double lot) {
		this.lot = lot;
	}

	public Double getBuildArea() {
		return buildArea;
	}

	public void setBuildArea(Double buildArea) {
		this.buildArea = buildArea;
	}

	public BigDecimal getBuyUnitPrice() {
		return buyUnitPrice;
	}

	public void setBuyUnitPrice(BigDecimal buyUnitPrice) {
		this.buyUnitPrice = buyUnitPrice;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getLoanTerm() {
		return loanTerm;
	}

	public void setLoanTerm(Integer loanTerm) {
		this.loanTerm = loanTerm;
	}

	public BigDecimal getRepayMonth() {
		return repayMonth;
	}

	public void setRepayMonth(BigDecimal repayMonth) {
		this.repayMonth = repayMonth;
	}

	public BigDecimal getLoanBalance() {
		return loanBalance;
	}

	public void setLoanBalance(BigDecimal loanBalance) {
		this.loanBalance = loanBalance;
	}

	public Boolean getRent() {
		return rent;
	}

	public void setRent(Boolean rent) {
		this.rent = rent;
	}

	public BigDecimal getRentAmount() {
		return rentAmount;
	}

	public void setRentAmount(BigDecimal rentAmount) {
		this.rentAmount = rentAmount;
	}

	public Date getRentBegin() {
		return rentBegin;
	}

	public void setRentBegin(Date rentBegin) {
		this.rentBegin = rentBegin;
	}

	public Date getRentEnd() {
		return rentEnd;
	}

	public void setRentEnd(Date rentEnd) {
		this.rentEnd = rentEnd;
	}
}


