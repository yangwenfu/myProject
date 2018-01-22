package com.xinyunlian.jinfu.audit.dto;

import com.xinyunlian.jinfu.audit.enums.EAuditNoteType;

import java.io.Serializable;

/**
 * @author willwang
 */
public class LoanAuditNoteDto implements Serializable {

    private String noteId;

    private String applId;

    private EAuditNoteType noteType;

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
