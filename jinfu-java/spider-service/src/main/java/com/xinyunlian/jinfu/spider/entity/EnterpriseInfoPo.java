package com.xinyunlian.jinfu.spider.entity;


import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by lenovo on on 2017/7/25.
 */
@Entity
@Table(name = "enterprise_inf")
public class EnterpriseInfoPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "legal_rep")
    private String legalRep; // 法人代表

    @Column(name = "register_capital")
    private String registerCapital;//注册资本

    @Column(name = "register_date")
    private String registerDate;//注册时间

    @Column(name = "manage_status")
    private String manageStatus; // 经营状态

    @Column(name = "busniess_reg_num")
    private String busniessRegNum; // 工商注册号

    @Column(name = "org_code")
    private String orgCode; // 组织机构代码

    @Column(name = "credit_code")
    private String creditCode;//统一信用代码

    @Column(name = "enterprise_type")
    private String enterpriseType;//企业类型

    @Column(name = "trade")
    private String trade;//行业

    @Column(name = "busniess_term")
    private String busniessTerm;//营业期限

    @Column(name = "approval_date")
    private String approvalDate;//核准日期

    @Column(name = "register_auth")
    private String registerAuth;//登记机关

    @Column(name = "register_adr")
    private String registerAdr;//注册地址

    @Column(name = "buniess_scope")
    private String buniessScope;//经营范围

    @Column(name="user_id")
    private String userId;

    @Column(name="company_rongzi")
    private String companyRongzi; // 融资历史(企业发展)
    @Column(name="company_teammember")
    private String companyTeammember; // 核心团队(企业发展)
    @Column(name="company_product")
    private String companyProduct; //企业业务(企业发展)
    @Column(name="jigou_tzanli")
    private String jigouTzanli; // 投资事件(企业发展)
    @Column(name="company_jingpin")
    private  String companyJingpin; // 竞品信息(企业发展)
    @Column(name="lawsuit_count")
    private  String lawsuitCount; // 法律诉讼(司法风险)
    @Column(name="court_count")
    private  String courtCount ; // 法院公告(司法风险)
    @Column(name="dishonest")
    private  String dishonest ; //  失信人(司法风险)
    @Column(name="zhixing")
    private  String  zhixing; // 被执行人(司法风险)
    @Column(name="abnormal_count")
    private  String abnormalCount ;// 经营异常(经营风险)
    @Column(name="punishment")
    private  String punishment ; // 行政处罚(经营风险)
    @Column(name="illegal_count")
    private  String illegalCount ;// 严重违法(经营风险)
    @Column(name="equity_count")
    private  String equityCount ;// 股权出质(经营风险)
    @Column(name="mortgage_count")
    private  String mortgageCount ;// 动产抵押(经营风险)
    @Column(name="own_tax_count")
    private  String ownTaxCount ;//  欠税公告(经营风险)
    @Column(name="bid_count")
    private  String bidCount ;//  招投标(经营状况)
    @Column(name="bond_count")
    private  String bondCount ;//  债券信息(经营状况)
    @Column(name="goudi_count")
    private  String goudiCount ;//  购地信息(经营状况)
    @Column(name="recruit_count")
    private  String recruitCount ;//  招聘(经营状况)
    @Column(name="tax_credit_count")
    private  String taxCreditCount ;//  税务评级(经营状况)
    @Column(name="check_count")
    private  String checkCount ;//  抽查检查(经营状况)
    @Column(name="productinfo")
    private  String productinfo ;//  产品信息(经营状况)
    @Column(name="qualification")
    private  String qualification ;//  资质证书(经营状况)
    @Column(name="tm_count")
    private  String tmCount ;// 商标信息(知识产权)
    @Column(name="patent_count")
    private  String patentCount ;// 专利(知识产权)
    @Column(name="cpoy_count")
    private  String cpoyCount ;// 著作权(知识产权)

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLegalRep() {
        return legalRep;
    }

    public void setLegalRep(String legalRep) {
        this.legalRep = legalRep;
    }

    public String getRegisterCapital() {
        return registerCapital;
    }

    public void setRegisterCapital(String registerCapital) {
        this.registerCapital = registerCapital;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getManageStatus() {
        return manageStatus;
    }

    public void setManageStatus(String manageStatus) {
        this.manageStatus = manageStatus;
    }

    public String getBusniessRegNum() {
        return busniessRegNum;
    }

    public void setBusniessRegNum(String busniessRegNum) {
        this.busniessRegNum = busniessRegNum;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getEnterpriseType() {
        return enterpriseType;
    }

    public void setEnterpriseType(String enterpriseType) {
        this.enterpriseType = enterpriseType;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getBusniessTerm() {
        return busniessTerm;
    }

    public void setBusniessTerm(String busniessTerm) {
        this.busniessTerm = busniessTerm;
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getRegisterAuth() {
        return registerAuth;
    }

    public void setRegisterAuth(String registerAuth) {
        this.registerAuth = registerAuth;
    }

    public String getRegisterAdr() {
        return registerAdr;
    }

    public void setRegisterAdr(String registerAdr) {
        this.registerAdr = registerAdr;
    }

    public String getBuniessScope() {
        return buniessScope;
    }

    public void setBuniessScope(String buniessScope) {
        this.buniessScope = buniessScope;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCompanyRongzi() {
        return companyRongzi;
    }

    public void setCompanyRongzi(String companyRongzi) {
        this.companyRongzi = companyRongzi;
    }

    public String getCompanyTeammember() {
        return companyTeammember;
    }

    public void setCompanyTeammember(String companyTeammember) {
        this.companyTeammember = companyTeammember;
    }

    public String getCompanyProduct() {
        return companyProduct;
    }

    public void setCompanyProduct(String companyProduct) {
        this.companyProduct = companyProduct;
    }

    public String getJigouTzanli() {
        return jigouTzanli;
    }

    public void setJigouTzanli(String jigouTzanli) {
        this.jigouTzanli = jigouTzanli;
    }

    public String getCompanyJingpin() {
        return companyJingpin;
    }

    public void setCompanyJingpin(String companyJingpin) {
        this.companyJingpin = companyJingpin;
    }

    public String getLawsuitCount() {
        return lawsuitCount;
    }

    public void setLawsuitCount(String lawsuitCount) {
        this.lawsuitCount = lawsuitCount;
    }

    public String getCourtCount() {
        return courtCount;
    }

    public void setCourtCount(String courtCount) {
        this.courtCount = courtCount;
    }

    public String getDishonest() {
        return dishonest;
    }

    public void setDishonest(String dishonest) {
        this.dishonest = dishonest;
    }

    public String getZhixing() {
        return zhixing;
    }

    public void setZhixing(String zhixing) {
        this.zhixing = zhixing;
    }

    public String getAbnormalCount() {
        return abnormalCount;
    }

    public void setAbnormalCount(String abnormalCount) {
        this.abnormalCount = abnormalCount;
    }

    public String getPunishment() {
        return punishment;
    }

    public void setPunishment(String punishment) {
        this.punishment = punishment;
    }

    public String getIllegalCount() {
        return illegalCount;
    }

    public void setIllegalCount(String illegalCount) {
        this.illegalCount = illegalCount;
    }

    public String getEquityCount() {
        return equityCount;
    }

    public void setEquityCount(String equityCount) {
        this.equityCount = equityCount;
    }

    public String getMortgageCount() {
        return mortgageCount;
    }

    public void setMortgageCount(String mortgageCount) {
        this.mortgageCount = mortgageCount;
    }

    public String getOwnTaxCount() {
        return ownTaxCount;
    }

    public void setOwnTaxCount(String ownTaxCount) {
        this.ownTaxCount = ownTaxCount;
    }

    public String getBidCount() {
        return bidCount;
    }

    public void setBidCount(String bidCount) {
        this.bidCount = bidCount;
    }

    public String getBondCount() {
        return bondCount;
    }

    public void setBondCount(String bondCount) {
        this.bondCount = bondCount;
    }

    public String getGoudiCount() {
        return goudiCount;
    }

    public void setGoudiCount(String goudiCount) {
        this.goudiCount = goudiCount;
    }

    public String getRecruitCount() {
        return recruitCount;
    }

    public void setRecruitCount(String recruitCount) {
        this.recruitCount = recruitCount;
    }

    public String getTaxCreditCount() {
        return taxCreditCount;
    }

    public void setTaxCreditCount(String taxCreditCount) {
        this.taxCreditCount = taxCreditCount;
    }

    public String getCheckCount() {
        return checkCount;
    }

    public void setCheckCount(String checkCount) {
        this.checkCount = checkCount;
    }

    public String getProductinfo() {
        return productinfo;
    }

    public void setProductinfo(String productinfo) {
        this.productinfo = productinfo;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getTmCount() {
        return tmCount;
    }

    public void setTmCount(String tmCount) {
        this.tmCount = tmCount;
    }

    public String getPatentCount() {
        return patentCount;
    }

    public void setPatentCount(String patentCount) {
        this.patentCount = patentCount;
    }

    public String getCpoyCount() {
        return cpoyCount;
    }

    public void setCpoyCount(String cpoyCount) {
        this.cpoyCount = cpoyCount;
    }
}


