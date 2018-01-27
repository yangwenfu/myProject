package com.xinyunlian.jinfu.audit.entity;

import com.xinyunlian.jinfu.audit.enums.EAuditNoteType;
import com.xinyunlian.jinfu.audit.enums.converter.ELoanAuditNoteTypeEnumConverter;
import com.xinyunlian.jinfu.common.entity.BaseMaintainablePo;
import com.xinyunlian.jinfu.common.entity.BasePo;

import javax.persistence.*;

/**
 * @author willwang
 */
@Entity
@Table(name = "FP_AUDIT_NOTE")
public class LoanAuditNotePo extends BaseMaintainablePo {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ADUIT_NOTE_ID")
    private String noteId;

    @Column(name = "APPL_ID")
    private String applId;

    @Convert(converter = ELoanAuditNoteTypeEnumConverter.class)
    @Column(name = "NOTE_TYPE")
    private EAuditNoteType noteType;

    @Column(name = "CONTENT")
    private String content;

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getApplId() {
        return applId;
    }

    public void setApplId(String applId) {
        this.applId = applId;
    }

    public EAuditNoteType getNoteType() {
        return noteType;
    }

    public void setNoteType(EAuditNoteType noteType) {
        this.noteType = noteType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
