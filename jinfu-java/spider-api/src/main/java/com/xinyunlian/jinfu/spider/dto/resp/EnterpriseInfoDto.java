package com.xinyunlian.jinfu.spider.dto.resp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on on 2017/7/25.
 */
public class EnterpriseInfoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private long enterpriseId; // 主键
    private String legalRep; // 法人代表
    private String registerCapital;//注册资本
    private String registerDate;//注册时间
    private String manageStatus; // 经营状态
    private String busniessRegNum; // 工商注册号
    private String orgCode; // 组织机构代码
    private String creditCode;//统一信用代码
    private String enterpriseType;//企业类型
    private String trade;//行业
    private String busniessTerm;//营业期限
    private String approvalDate;//核准日期
    private String registerAuth;//登记机关
    private String registerAdr;//注册地址
    private String buniessScope;//经营范围
    private String userId;  //用户id

    private String companyRongzi; // 融资历史(企业发展)
    private String companyTeammember; // 核心团队(企业发展)
    private String companyProduct; //企业业务(企业发展)
    private String jigouTzanli; // 投资事件(企业发展)
    private  String companyJingpin; // 竞品信息(企业发展)

    private  String lawsuitCount; // 法律诉讼(司法风险)
    private  String courtCount ; // 法院公告(司法风险)
    private  String dishonest ; //  失信人(司法风险)
    private  String  zhixing; // 被执行人(司法风险)

    private  String abnormalCount ;// 经营异常(经营风险)
    private  String punishment ; // 行政处罚(经营风险)
    private  String illegalCount ;// 严重违法(经营风险)
    private  String equityCount ;// 股权出质(经营风险)
    private  String mortgageCount ;// 动产抵押(经营风险)
    private  String ownTaxCount ;//  欠税公告(经营风险)

    private  String bidCount ;//  招投标(经营状况)
    private  String bondCount ;//  债券信息(经营状况)
    private  String goudiCount ;//  购地信息(经营状况)
    private  String recruitCount ;//  招聘(经营状况)
    private  String taxCreditCount ;//  税务评级(经营状况)
    private  String checkCount ;//  抽查检查(经营状况)
    private  String productinfo ;//  产品信息(经营状况)
    private  String qualification ;//  资质证书(经营状况)

    private  String tmCount ;// 商标信息(知识产权)
    private  String patentCount ;// 专利(知识产权)
    private  String cpoyCount ;// 著作权(知识产权)


    public long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(long enterpriseId) {
        this.enterpriseId = enterpriseId;
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
