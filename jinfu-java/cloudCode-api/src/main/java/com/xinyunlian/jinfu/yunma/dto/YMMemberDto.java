package com.xinyunlian.jinfu.yunma.dto;

import com.xinyunlian.jinfu.yunma.enums.EMemberAuditStatus;
import com.xinyunlian.jinfu.yunma.enums.EMemberIntoStatus;
import com.xinyunlian.jinfu.yunma.enums.EMemberStatus;
import com.xinyunlian.jinfu.yunma.enums.ESettlement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 云码商铺Entity
 *
 * @author jll
 */

public class YMMemberDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long storeId;
    private String userId;
    private String memberNo;
    private String qrcodeNo;
    private String qrcodeUrl;
    private String openId;
    private ESettlement settlement;
    private Date bindTime;
    private Long bankCardId;
    private Long corpBankId;
    private EMemberStatus memberStatus;
    private EMemberAuditStatus memberAuditStatus;
    private EMemberIntoStatus memberIntoStatus;
    private Boolean activate;//用户云码激活状态
    private String dealerUserId;
    private List<YmMemberBizDto> memberBizDtos = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo;
    }

    public String getQrcodeNo() {
        return qrcodeNo;
    }

    public void setQrcodeNo(String qrcodeNo) {
        this.qrcodeNo = qrcodeNo;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public ESettlement getSettlement() {
        return settlement;
    }

    public void setSettlement(ESettlement settlement) {
        this.settlement = settlement;
    }

    public Date getBindTime() {
        return bindTime;
    }

    public void setBindTime(Date bindTime) {
        this.bindTime = bindTime;
    }

    public Long getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(Long bankCardId) {
        this.bankCardId = bankCardId;
    }

    public EMemberStatus getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(EMemberStatus memberStatus) {
        this.memberStatus = memberStatus;
    }

    public List<YmMemberBizDto> getMemberBizDtos() {
        return memberBizDtos;
    }

    public void setMemberBizDtos(List<YmMemberBizDto> memberBizDtos) {
        this.memberBizDtos = memberBizDtos;
    }

    public EMemberAuditStatus getMemberAuditStatus() {
        return memberAuditStatus;
    }

    public void setMemberAuditStatus(EMemberAuditStatus memberAuditStatus) {
        this.memberAuditStatus = memberAuditStatus;
    }

    public Boolean getActivate() {
        return activate;
    }

    public void setActivate(Boolean activate) {
        this.activate = activate;
    }

    public Long getCorpBankId() {
        return corpBankId;
    }

    public void setCorpBankId(Long corpBankId) {
        this.corpBankId = corpBankId;
    }

    public EMemberIntoStatus getMemberIntoStatus() {
        return memberIntoStatus;
    }

    public void setMemberIntoStatus(EMemberIntoStatus memberIntoStatus) {
        this.memberIntoStatus = memberIntoStatus;
    }

    public String getDealerUserId() {
        return dealerUserId;
    }

    public void setDealerUserId(String dealerUserId) {
        this.dealerUserId = dealerUserId;
    }
}


