package com.xinyunlian.jinfu.house.dto;

import com.xinyunlian.jinfu.picture.dto.PictureDto;
import com.xinyunlian.jinfu.user.enums.EHouseProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户房产信息Entity
 *
 * @author jll
 */

public class UserHouseDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String userId;
    //房产所有人
    private String ownerName;
    //共有人
    private String shareName;
    //共同居住者
    private String housemate;
    //购买时间
    private Date buyDate;
    //房产地址
    private String address;
    //所属区域
    private String area;
    //住宅类型
    private EHouseProperty property;
    //产权比例
    private Double lot;
    //建筑面积
    private Double buildArea;
    //购买单价
    private BigDecimal buyUnitPrice;
    //房龄
    private Integer age;
    //贷款年限
    private Integer loanTerm;
    //贷款月供
    private BigDecimal repayMonth;
    //贷款余额
    private BigDecimal loanBalance;
    //是否出租
    private Boolean rent;
    //月租金额
    private BigDecimal rentAmount;
    //租赁开始
    private Date rentBegin;
    //租赁结束
    private Date rentEnd;

    private List<PictureDto> pictureDtos = new ArrayList<>();

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

    public List<PictureDto> getPictureDtos() {
        return pictureDtos;
    }

    public void setPictureDtos(List<PictureDto> pictureDtos) {
        this.pictureDtos = pictureDtos;
    }

    @Override
    public String toString() {
        return "UserHouseDto{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", shareName='" + shareName + '\'' +
                ", housemate='" + housemate + '\'' +
                ", buyDate=" + buyDate +
                ", address='" + address + '\'' +
                ", area='" + area + '\'' +
                ", property=" + property +
                ", lot=" + lot +
                ", buildArea=" + buildArea +
                ", buyUnitPrice=" + buyUnitPrice +
                ", age=" + age +
                ", loanTerm=" + loanTerm +
                ", repayMonth=" + repayMonth +
                ", loanBalance=" + loanBalance +
                ", rent=" + rent +
                ", rentAmount=" + rentAmount +
                ", rentBegin=" + rentBegin +
                ", rentEnd=" + rentEnd +
                ", pictureDtos=" + pictureDtos +
                '}';
    }
}


