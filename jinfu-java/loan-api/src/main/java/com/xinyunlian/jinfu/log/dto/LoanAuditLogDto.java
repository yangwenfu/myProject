package com.xinyunlian.jinfu.log.dto;

import com.xinyunlian.jinfu.log.enums.ELoanAuditLogType;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jl062 on 2017/2/20.
 */
public class LoanAuditLogDto implements Serializable{

    private String applId;

    private String userName;

    private Date operateDate;

    private ELoanAuditLogType type;

    private String content;

    public LoanAuditLogDto() {

    }

    public LoanAuditLogDto(String userName, String applId, ELoanAuditLogType type, String... args) {
        this.userName = userName;
        this.applId = applId;
        this.type = type;
        this.content = type.getContent(args);
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

    public Date getOperateDate() {
        return operateDate;
    }

    public void setOperateDate(Date operateDate) {
        this.operateDate = operateDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public ELoanAuditLogType getType() {
        return type;
    }

    public void setType(ELoanAuditLogType type) {
        this.type = type;
    }
}
