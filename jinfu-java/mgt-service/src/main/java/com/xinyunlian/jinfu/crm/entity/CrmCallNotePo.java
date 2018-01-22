package com.xinyunlian.jinfu.crm.entity;

import com.xinyunlian.jinfu.common.entity.BasePo;

import javax.persistence.*;

/**
 * 来访电话处理记录Entity
 *
 * @author jll
 */
@Entity
@Table(name = "CRM_CALL_NOTE")
public class CrmCallNotePo extends BasePo {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CALL_NOTE_ID")
    private Long callNoteId;

    @Column(name = "CALL_ID")
    private Long callId;

    @Column(name = "CONTENT")
    private String content;

    @Column(name = "CREATE_NAME")
    private String createName;

    public void setCallNoteId(Long callNoteId) {
        this.callNoteId = callNoteId;
    }

    public Long getCallNoteId() {
        return callNoteId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public Long getCallId() {
        return callId;
    }

    public void setCallId(Long callId) {
        this.callId = callId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }
}


