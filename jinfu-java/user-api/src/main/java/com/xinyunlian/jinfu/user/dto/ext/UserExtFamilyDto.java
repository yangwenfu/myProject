package com.xinyunlian.jinfu.user.dto.ext;

import com.xinyunlian.jinfu.user.enums.EMarryStatus;
import com.xinyunlian.jinfu.user.enums.ESalaryMode;

import java.util.Date;

/**
 * Created by King on 2017/2/17.
 */
public class UserExtFamilyDto extends UserExtIdDto{
    private static final long serialVersionUID = 9155932424275331352L;
    //婚姻状况
    private EMarryStatus marryStatus;
    //家庭人口数
    private Integer familyNum;
    //子女人数
    private Integer childrenNum;
    //供养人数
    private Integer supportNum;
    //受薪形式
    private ESalaryMode salaryMode;
    //单位地址
    private String companyAdd;
    //工作单位
    private String company;
    //单位性质
    private String companyType;
    //企业规模
    private String companySize;
    //职务
    private String position;
    //单位电话
    private String companyPhone;
    //入职时间
    private Date employDate;

    public EMarryStatus getMarryStatus() {
        return marryStatus;
    }

    public void setMarryStatus(EMarryStatus marryStatus) {
        this.marryStatus = marryStatus;
    }

    public Integer getFamilyNum() {
        return familyNum;
    }

    public void setFamilyNum(Integer familyNum) {
        this.familyNum = familyNum;
    }

    public Integer getChildrenNum() {
        return childrenNum;
    }

    public void setChildrenNum(Integer childrenNum) {
        this.childrenNum = childrenNum;
    }

    public Integer getSupportNum() {
        return supportNum;
    }

    public void setSupportNum(Integer supportNum) {
        this.supportNum = supportNum;
    }

    public ESalaryMode getSalaryMode() {
        return salaryMode;
    }

    public void setSalaryMode(ESalaryMode salaryMode) {
        this.salaryMode = salaryMode;
    }

    public String getCompanyAdd() {
        return companyAdd;
    }

    public void setCompanyAdd(String companyAdd) {
        this.companyAdd = companyAdd;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getCompanySize() {
        return companySize;
    }

    public void setCompanySize(String companySize) {
        this.companySize = companySize;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public Date getEmployDate() {
        return employDate;
    }

    public void setEmployDate(Date employDate) {
        this.employDate = employDate;
    }
}
