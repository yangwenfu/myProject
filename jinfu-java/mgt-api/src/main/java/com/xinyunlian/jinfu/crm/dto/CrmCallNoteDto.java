package com.xinyunlian.jinfu.crm.dto;

import com.xinyunlian.jinfu.common.dto.BaseDto;

/**
 * 来访电话处理记录Entity
 *
 * @author jll
 */

public class CrmCallNoteDto extends BaseDto {
    private static final long serialVersionUID = 1L;
    private Long callNoteId;
    private Long callId;
    private String content;
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


