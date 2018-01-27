package com.xinyunlian.jinfu.log.entity;

import com.xinyunlian.jinfu.common.entity.BasePo;
import com.xinyunlian.jinfu.common.entity.id.IdInjectionEntityListener;
import com.xinyunlian.jinfu.loan.enums.converter.ELoanAuditLogTypeConverter;
import com.xinyunlian.jinfu.log.enums.ELoanAuditLogType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jl062 on 2017/2/20.
 */
@Entity
@Table(name = "fp_loan_audit_log")
@EntityListeners(IdInjectionEntityListener.class)
public class LoanAuditLogPo extends BasePo {

    @Id
    @Column(name = "LOG_ID")
    private String logId;

    @Column(name = "APPL_ID")
    private String applId;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "OPERATE_DT")
    private Date operateDate;

    @Convert(converter = ELoanAuditLogTypeConverter.class)
    @Column(name = "LOG_TYPE")
    private ELoanAuditLogType type;

    @Column(name = "CONTENT")
    private String content;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
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

    public ELoanAuditLogType getType() {
        return type;
    }

    public void setType(ELoanAuditLogType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
