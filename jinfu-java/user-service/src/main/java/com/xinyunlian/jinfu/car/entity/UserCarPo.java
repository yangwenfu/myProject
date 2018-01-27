package com.xinyunlian.jinfu.car.entity;

import com.xinyunlian.jinfu.car.enums.ECarPrice;
import com.xinyunlian.jinfu.car.enums.ECarProperty;
import com.xinyunlian.jinfu.car.enums.ECarRegMode;
import com.xinyunlian.jinfu.car.enums.ECarType;
import com.xinyunlian.jinfu.car.enums.converter.ECarPriceConverter;
import com.xinyunlian.jinfu.car.enums.converter.ECarPropertyConverter;
import com.xinyunlian.jinfu.car.enums.converter.ECarRegModeConverter;
import com.xinyunlian.jinfu.car.enums.converter.ECarTypeConverter;
import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户车辆信息Entity
 * @author jll
 * @version 
 */
@Entity
@Table(name = "USER_CAR")
public class UserCarPo extends BaseMaintainablePo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private Long id;

	@Column(name="USER_ID")
	private String userId;

	@Column(name="BRAND")
	private String brand;

	@Column(name="SERIES")
	private String series;

	@Column(name="CONFIG")
	private String config;

	@Column(name="PROPERTY")
	@Convert(converter = ECarPropertyConverter.class)
	private ECarProperty property;

	@Column(name="TYPE")
	@Convert(converter = ECarTypeConverter.class)
	private ECarType type;

	@Column(name="VEHICLE_LICENCE_PLATE_NO")
	private String vehicleLicencePlateNo;

	@Column(name="REG_AREA")
	private String regArea;

	@Column(name="REG_MODE")
	@Convert(converter = ECarRegModeConverter.class)
	private ECarRegMode regMode;

	@Column(name="REG_DATE")
	private Date regDate;

	@Column(name="PRODUCT_DATE")
	private Date productDate;

	@Column(name="PRICE")
	@Convert(converter = ECarPriceConverter.class)
	private ECarPrice price;

	@Column(name="DOWN_PAYMENT")
	private Double downPayment;

	@Column(name="LOAN_TERM")
	private Integer loanTerm;

	@Column(name="LOAN_MONTH")
	private String loanMonth;

	@Column(name="LOAN_BALANCE")
	private BigDecimal loanBalance;

	@Column(name="IS_MORTGAGE")
	private Boolean mortgage;

	@Column(name="HAS_GPS")
	private Boolean hasGps;

	@Column(name="ENGINE_NO")
	private String engineNo;

	@Column(name="VIN_NO")
	private String vinNo;

	@Column(name="HAS_INSURANCE")
	private Boolean hasInsurance;

	@Column(name="COMPEL_POLICY_NO")
	private String compelPolicyNo;

	@Column(name="MILEAGE")
	private Double mileage;

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

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public ECarProperty getProperty() {
		return property;
	}

	public void setProperty(ECarProperty property) {
		this.property = property;
	}

	public ECarType getType() {
		return type;
	}

	public void setType(ECarType type) {
		this.type = type;
	}

	public String getVehicleLicencePlateNo() {
		return vehicleLicencePlateNo;
	}

	public void setVehicleLicencePlateNo(String vehicleLicencePlateNo) {
		this.vehicleLicencePlateNo = vehicleLicencePlateNo;
	}

	public String getRegArea() {
		return regArea;
	}

	public void setRegArea(String regArea) {
		this.regArea = regArea;
	}

	public ECarRegMode getRegMode() {
		return regMode;
	}

	public void setRegMode(ECarRegMode regMode) {
		this.regMode = regMode;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getProductDate() {
		return productDate;
	}

	public void setProductDate(Date productDate) {
		this.productDate = productDate;
	}

	public ECarPrice getPrice() {
		return price;
	}

	public void setPrice(ECarPrice price) {
		this.price = price;
	}

	public Double getDownPayment() {
		return downPayment;
	}

	public void setDownPayment(Double downPayment) {
		this.downPayment = downPayment;
	}

	public Integer getLoanTerm() {
		return loanTerm;
	}

	public void setLoanTerm(Integer loanTerm) {
		this.loanTerm = loanTerm;
	}

	public String getLoanMonth() {
		return loanMonth;
	}

	public void setLoanMonth(String loanMonth) {
		this.loanMonth = loanMonth;
	}

	public BigDecimal getLoanBalance() {
		return loanBalance;
	}

	public void setLoanBalance(BigDecimal loanBalance) {
		this.loanBalance = loanBalance;
	}

	public Boolean getMortgage() {
		return mortgage;
	}

	public void setMortgage(Boolean mortgage) {
		this.mortgage = mortgage;
	}

	public Boolean getHasGps() {
		return hasGps;
	}

	public void setHasGps(Boolean hasGps) {
		this.hasGps = hasGps;
	}

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public String getVinNo() {
		return vinNo;
	}

	public void setVinNo(String vinNo) {
		this.vinNo = vinNo;
	}

	public Boolean getHasInsurance() {
		return hasInsurance;
	}

	public void setHasInsurance(Boolean hasInsurance) {
		this.hasInsurance = hasInsurance;
	}

	public String getCompelPolicyNo() {
		return compelPolicyNo;
	}

	public void setCompelPolicyNo(String compelPolicyNo) {
		this.compelPolicyNo = compelPolicyNo;
	}

	public Double getMileage() {
		return mileage;
	}

	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}
}


