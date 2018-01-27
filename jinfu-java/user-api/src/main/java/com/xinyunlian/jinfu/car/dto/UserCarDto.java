package com.xinyunlian.jinfu.car.dto;

import com.xinyunlian.jinfu.car.enums.ECarPrice;
import com.xinyunlian.jinfu.car.enums.ECarProperty;
import com.xinyunlian.jinfu.car.enums.ECarRegMode;
import com.xinyunlian.jinfu.car.enums.ECarType;
import com.xinyunlian.jinfu.picture.dto.PictureDto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户车辆信息Entity
 * @author jll
 * @version 
 */

public class UserCarDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String userId;
	//车辆品牌
	private String brand;
	//车系
	private String series;
	//车型
	private String config;
	//车辆属性
	private ECarProperty property;
	//车辆类型
	private ECarType type;
	//车牌号码
	private String vehicleLicencePlateNo;
	//上牌地
	private String regArea;
	//上牌方式
	private ECarRegMode regMode;
	//上牌年月
	private Date regDate;
	//出厂日期
	private Date productDate;
	//车辆价值
	private ECarPrice price;
	//首付款比例
	private Double downPayment;
	//车贷分期期数
	private Integer loanTerm;
	//车贷月供
	private String loanMonth;
	//车贷余额
	private BigDecimal loanBalance;
	//车贷是否抵押担保
	private Boolean mortgage = false;
	//是否GPS装置
	private Boolean hasGps = false;
	//发动机号
	private String engineNo;
	//车辆车架号
	private String vinNo;
	//交强险保险公司
	private Boolean hasInsurance;
	//交强险保单号
	private String compelPolicyNo;
	//形式里程
	private Double mileage;

	List<PictureDto> pictureDtos = new ArrayList<>();

	@Override
	public String toString() {
		return "UserCarDto{" +
				"id=" + id +
				", userId='" + userId + '\'' +
				", brand='" + brand + '\'' +
				", series='" + series + '\'' +
				", config='" + config + '\'' +
				", property=" + property +
				", type=" + type +
				", vehicleLicencePlateNo='" + vehicleLicencePlateNo + '\'' +
				", regArea='" + regArea + '\'' +
				", regMode=" + regMode +
				", regDate=" + regDate +
				", productDate=" + productDate +
				", price=" + price +
				", downPayment=" + downPayment +
				", loanTerm=" + loanTerm +
				", loanMonth='" + loanMonth + '\'' +
				", loanBalance=" + loanBalance +
				", mortgage=" + mortgage +
				", hasGps=" + hasGps +
				", engineNo='" + engineNo + '\'' +
				", vinNo='" + vinNo + '\'' +
				", hasInsurance=" + hasInsurance +
				", compelPolicyNo='" + compelPolicyNo + '\'' +
				", mileage=" + mileage +
				", pictureDtos=" + pictureDtos +
				'}';
	}

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

	public List<PictureDto> getPictureDtos() {
		return pictureDtos;
	}

	public void setPictureDtos(List<PictureDto> pictureDtos) {
		this.pictureDtos = pictureDtos;
	}
}


