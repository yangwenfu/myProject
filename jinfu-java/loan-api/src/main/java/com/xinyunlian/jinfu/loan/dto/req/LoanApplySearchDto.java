package com.xinyunlian.jinfu.loan.dto.req;

import com.xinyunlian.jinfu.appl.enums.*;
import com.xinyunlian.jinfu.audit.enums.EAuditStatus;
import com.xinyunlian.jinfu.common.dto.PagingDto;
import com.xinyunlian.jinfu.loan.dto.resp.apply.LoanApplyListEachDto;
import com.xinyunlian.jinfu.loan.enums.EApplStatus;
import com.xinyunlian.jinfu.loan.enums.ETransferStat;
import com.xinyunlian.jinfu.router.enums.EFinanceSourceType;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author willwang
 */
public class LoanApplySearchDto extends PagingDto<LoanApplyListEachDto> {
    private String applId;

    /**
     * 申请人
     */
    private String userName;

    /**
     * 身份证
     */
    private String idCardNo;

    /**
     * 申请开始时间
     */
    private String applStartDate;

    /**
     * 申请结束时间
     */
    private String applEndDate;

    /**
     * 签约开始时间
     */
    private String signStartDate;

    /**
     * 签约结束时间
     */
    private String signEndDate;

    /**
     * 放款开始时间
     */
    private String transferStartDate;

    /**
     * 放款结束时间
     */
    private String transferEndDate;

    private String prodId;

    private EApplStatus applStatus;

    private Set<String> userIds = new HashSet<>();

    private Set<String> appIds = new HashSet<>();

    /**
     * 初审分配状态
     */
    private ELoanApplClaimedType trialClaimedType;

    private ELoanApplDealerType dealerType;

    private ELoanApplSortType sortType;

    private ELoanApplTrialType trialType;

    /**
     * 签约状态
     */
    private ELoanApplSignType signType;

    /**
     * 是否挂起
     */
    private Boolean hangup;

    /**
     * 初审领取人
     */
    private String trialUserId;

    /**
     * 终审领取人
     */
    private String reviewUserId;

    /**
     * 初审领取人
     */
    private String trialName;

    /**
     * 初审领取人列表
     */
    private Collection<String> trialUserIds;

    /**
     * 终审领取人
     */
    private Collection<String> reviewUserIds;

    /**
     * 终审人
     */
    private String reviewName;

    /**
     * 烟草许可证
     */
    private String tobacco;

    /**
     * 手机号
     * @return
     */
    private String mobile;

    /**
     * 联系人手机号
     */
    private String linkmanMobile;

    /**
     * 店铺名
     */
    private String storeName;

    /**
     * 初审意见
     */
    private EAuditStatus trialStatus;

    /**
     * 终审状态
     */
    private ELoanApplReviewType reviewType;

    /**
     * 终审意见
     */
    private EAuditStatus reviewStatus;

    /**
     * 转账状态
     */
    private ETransferStat transferStat;

    /**
     * 资金来源
     */
    private EFinanceSourceType financeSourceType;

    public EFinanceSourceType getFinanceSourceType() {
        return financeSourceType;
    }

    public void setFinanceSourceType(EFinanceSourceType financeSourceType) {
        this.financeSourceType = financeSourceType;
    }

    public String getReviewUserId() {
        return reviewUserId;
    }

    public void setReviewUserId(String reviewUserId) {
        this.reviewUserId = reviewUserId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getLinkmanMobile() {
        return linkmanMobile;
    }

    public void setLinkmanMobile(String linkmanMobile) {
        this.linkmanMobile = linkmanMobile;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getSignStartDate() {
        return signStartDate;
    }

    public void setSignStartDate(String signStartDate) {
        this.signStartDate = signStartDate;
    }

    public String getSignEndDate() {
        return signEndDate;
    }

    public void setSignEndDate(String signEndDate) {
        this.signEndDate = signEndDate;
    }

    public String getTransferStartDate() {
        return transferStartDate;
    }

    public void setTransferStartDate(String transferStartDate) {
        this.transferStartDate = transferStartDate;
    }

    public String getTransferEndDate() {
        return transferEndDate;
    }

    public void setTransferEndDate(String transferEndDate) {
        this.transferEndDate = transferEndDate;
    }

    public String getTrialName() {
        return trialName;
    }

    public void setTrialName(String trialName) {
        this.trialName = trialName;
    }

    public Collection<String> getTrialUserIds() {
        return trialUserIds;
    }

    public void setTrialUserIds(Collection<String> trialUserIds) {
        this.trialUserIds = trialUserIds;
    }

    public String getReviewName() {
        return reviewName;
    }

    public void setReviewName(String reviewName) {
        this.reviewName = reviewName;
    }

    public Collection<String> getReviewUserIds() {
        return reviewUserIds;
    }

    public void setReviewUserIds(Collection<String> reviewUserIds) {
        this.reviewUserIds = reviewUserIds;
    }

    public ETransferStat getTransferStat() {
        return transferStat;
    }

    public void setTransferStat(ETransferStat transferStat) {
        this.transferStat = transferStat;
    }

    public ELoanApplSignType getSignType() {
        return signType;
    }

    public void setSignType(ELoanApplSignType signType) {
        this.signType = signType;
    }

    public ELoanApplReviewType getReviewType() {
        return reviewType;
    }

    public void setReviewType(ELoanApplReviewType reviewType) {
        this.reviewType = reviewType;
    }

    public EAuditStatus getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(EAuditStatus reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public EAuditStatus getTrialStatus() {
        return trialStatus;
    }

    public void setTrialStatus(EAuditStatus trialStatus) {
        this.trialStatus = trialStatus;
    }

    public String getTobacco() {
        return tobacco;
    }

    public void setTobacco(String tobacco) {
        this.tobacco = tobacco;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTrialUserId() {
        return trialUserId;
    }

    public void setTrialUserId(String trialUserId) {
        this.trialUserId = trialUserId;
    }

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public EApplStatus getApplStatus() {
        return applStatus;
    }

    public void setApplStatus(EApplStatus applStatus) {
        this.applStatus = applStatus;
    }

    public Set<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(Set<String> userIds) {
        this.userIds = userIds;
    }

    public String getApplStartDate() {
        return applStartDate;
    }

    public void setApplStartDate(String applStartDate) {
        this.applStartDate = applStartDate;
    }

    public String getApplEndDate() {
        return applEndDate;
    }

    public void setApplEndDate(String applEndDate) {
        this.applEndDate = applEndDate;
    }

    public Set<String> getAppIds() {
        return appIds;
    }

    public void setAppIds(Set<String> appIds) {
        this.appIds = appIds;
    }

    public ELoanApplClaimedType getTrialClaimedType() {
        return trialClaimedType;
    }

    public void setTrialClaimedType(ELoanApplClaimedType trialClaimedType) {
        this.trialClaimedType = trialClaimedType;
    }

    public ELoanApplDealerType getDealerType() {
        return dealerType;
    }

    public void setDealerType(ELoanApplDealerType dealerType) {
        this.dealerType = dealerType;
    }

    public ELoanApplSortType getSortType() {
        return sortType;
    }

    public void setSortType(ELoanApplSortType sortType) {
        this.sortType = sortType;
    }

    public ELoanApplTrialType getTrialType() {
        return trialType;
    }

    public void setTrialType(ELoanApplTrialType trialType) {
        this.trialType = trialType;
    }

    public Boolean getHangup() {
        return hangup;
    }

    public void setHangup(Boolean hangup) {
        this.hangup = hangup;
    }
}
