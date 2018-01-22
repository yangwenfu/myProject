package com.xinyunlian.jinfu.audit.dto.resp;

import java.io.Serializable;
import java.util.Date;

/**
 * @author willwang
 */
public class LoanAuditNoteRespDto implements Serializable {

    private String id;

    private String mgtUserId;

    private String mgtUserName;

    private String content;

    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMgtUserId() {
        return mgtUserId;
    }

    public void setMgtUserId(String mgtUserId) {
        this.mgtUserId = mgtUserId;
    }

    public String getMgtUserName() {
        return mgtUserName;
    }

    public void setMgtUserName(String mgtUserName) {
        this.mgtUserName = mgtUserName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
