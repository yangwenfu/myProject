package com.xinyunlian.jinfu.audit.dto.req;

import com.xinyunlian.jinfu.audit.enums.EAuditNoteType;

import java.io.Serializable;

/**
 * @author willwang
 */
public class LoanAuditNoteSearchDto implements Serializable {

    private String applId;

    private EAuditNoteType auditNoteType;

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }

    public EAuditNoteType getAuditNoteType() {
        return auditNoteType;
    }

    public void setAuditNoteType(EAuditNoteType auditNoteType) {
        this.auditNoteType = auditNoteType;
    }

}
